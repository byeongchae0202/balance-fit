# Server Backend Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Balance Fit 앱 연동을 위한 최소 REST 백엔드 서버를 저장소에 추가하고 로컬 실행 가능한 상태로 만든다.

**Architecture:** `server/` 하위에 Express 앱을 구성하고 라우트(`health`, `calibration`, `guidance`)를 모듈 분리한다. 캘리브레이션 데이터는 메모리 저장소로 유지하며, 입력 검증 실패와 서버 오류를 JSON 에러 형태로 명시적으로 응답한다.

**Tech Stack:** Node.js, Express, Jest, Supertest

---

## File Structure Map

- Create: `server/package.json` — 서버 의존성/스크립트
- Create: `server/src/index.js` — 서버 실행 진입점
- Create: `server/src/app.js` — Express 앱 조립
- Create: `server/src/routes/health.js` — 헬스체크 라우트
- Create: `server/src/routes/calibration.js` — 캘리브레이션 라우트
- Create: `server/src/routes/guidance.js` — 가이드 라우트
- Create: `server/src/store/calibrationStore.js` — 최근 캘리브레이션 메모리 저장소
- Create: `server/src/domain/guidance.js` — 각도 기반 가이드 규칙 함수
- Create: `server/test/health.test.js` — 헬스체크 통합 테스트
- Create: `server/test/calibration.test.js` — 캘리브레이션 API 테스트
- Create: `server/test/guidance.test.js` — 가이드 API 테스트
- Modify: `README.md` — 서버 실행 안내 추가
- Modify: `docs/getting-started.md` — 서버 개발 시작 절차 추가

### Task 1: Bootstrapping Server Runtime

**Files:**
- Create: `server/package.json`
- Create: `server/src/index.js`
- Create: `server/src/app.js`

- [ ] **Step 1: Write failing health test scaffold**

```js
// server/test/health.test.js
const request = require('supertest');
const app = require('../src/app');

describe('GET /health', () => {
  it('returns ok status', async () => {
    const res = await request(app).get('/health');
    expect(res.statusCode).toBe(200);
    expect(res.body).toEqual({ status: 'ok' });
  });
});
```

- [ ] **Step 2: Run test to verify it fails**

Run: `npm --prefix server test -- health.test.js`  
Expected: FAIL with `Cannot find module '../src/app'`

- [ ] **Step 3: Create minimal package and app bootstrap**

```json
{
  "name": "balance-fit-server",
  "version": "1.0.0",
  "private": true,
  "main": "src/index.js",
  "scripts": {
    "start": "node src/index.js",
    "test": "jest --runInBand"
  },
  "dependencies": {
    "express": "^4.19.2"
  },
  "devDependencies": {
    "jest": "^29.7.0",
    "supertest": "^7.0.0"
  }
}
```

```js
// server/src/app.js
const express = require('express');
const app = express();
app.use(express.json());
app.get('/health', (req, res) => res.json({ status: 'ok' }));
module.exports = app;
```

```js
// server/src/index.js
const app = require('./app');
const port = Number(process.env.PORT || 8080);
app.listen(port, () => {
  console.log(`balance-fit-server listening on ${port}`);
});
```

- [ ] **Step 4: Install deps and run test to pass**

Run: `npm --prefix server install`  
Run: `npm --prefix server test -- health.test.js`  
Expected: PASS

- [ ] **Step 5: Commit**

```bash
git add server/package.json server/src/index.js server/src/app.js server/test/health.test.js
git commit -m "feat(server): bootstrap express runtime"
```

### Task 2: Implementing Calibration API

**Files:**
- Create: `server/src/store/calibrationStore.js`
- Create: `server/src/routes/calibration.js`
- Modify: `server/src/app.js`
- Create: `server/test/calibration.test.js`

- [ ] **Step 1: Write failing calibration tests**

```js
// server/test/calibration.test.js
const request = require('supertest');
const app = require('../src/app');

describe('Calibration API', () => {
  it('stores calibration', async () => {
    const payload = { offsetX: 0.5, offsetY: -0.2, profile: 'indoor' };
    const res = await request(app).post('/api/v1/calibration').send(payload);
    expect(res.statusCode).toBe(201);
    expect(res.body.saved).toBe(true);
  });

  it('returns latest calibration', async () => {
    const res = await request(app).get('/api/v1/calibration/latest');
    expect(res.statusCode).toBe(200);
    expect(res.body.profile).toBeDefined();
  });
});
```

- [ ] **Step 2: Run calibration tests to verify fail**

Run: `npm --prefix server test -- calibration.test.js`  
Expected: FAIL with 404 for `/api/v1/calibration`

- [ ] **Step 3: Add minimal store and routes with validation**

```js
// server/src/store/calibrationStore.js
let latest = null;
function saveCalibration(data) { latest = { ...data, savedAt: new Date().toISOString() }; return latest; }
function getLatestCalibration() { return latest; }
module.exports = { saveCalibration, getLatestCalibration };
```

```js
// server/src/routes/calibration.js
const express = require('express');
const { saveCalibration, getLatestCalibration } = require('../store/calibrationStore');

const router = express.Router();
router.post('/', (req, res) => {
  const { offsetX, offsetY, profile } = req.body || {};
  if (typeof offsetX !== 'number' || typeof offsetY !== 'number' || typeof profile !== 'string') {
    return res.status(400).json({ code: 'INVALID_CALIBRATION_INPUT', message: 'offsetX, offsetY(number), profile(string) are required' });
  }
  const saved = saveCalibration({ offsetX, offsetY, profile });
  return res.status(201).json({ saved: true, calibration: saved });
});
router.get('/latest', (req, res) => {
  const latest = getLatestCalibration();
  if (!latest) return res.status(404).json({ code: 'CALIBRATION_NOT_FOUND', message: 'No calibration data' });
  return res.json(latest);
});
module.exports = router;
```

```js
// server/src/app.js (append)
const calibrationRouter = require('./routes/calibration');
app.use('/api/v1/calibration', calibrationRouter);
```

- [ ] **Step 4: Run tests and verify pass**

Run: `npm --prefix server test -- calibration.test.js`  
Expected: PASS

- [ ] **Step 5: Commit**

```bash
git add server/src/store/calibrationStore.js server/src/routes/calibration.js server/src/app.js server/test/calibration.test.js
git commit -m "feat(server): add calibration endpoints"
```

### Task 3: Implementing Guidance API and Error Boundary

**Files:**
- Create: `server/src/domain/guidance.js`
- Create: `server/src/routes/guidance.js`
- Modify: `server/src/app.js`
- Create: `server/test/guidance.test.js`

- [ ] **Step 1: Write failing guidance tests**

```js
const request = require('supertest');
const app = require('../src/app');

describe('Guidance API', () => {
  it('returns guidance for angle', async () => {
    const res = await request(app).post('/api/v1/guidance').send({ angle: 6 });
    expect(res.statusCode).toBe(200);
    expect(res.body.level).toBe('adjust');
  });
});
```

- [ ] **Step 2: Run tests to verify fail**

Run: `npm --prefix server test -- guidance.test.js`  
Expected: FAIL with 404

- [ ] **Step 3: Add guidance domain and route**

```js
// server/src/domain/guidance.js
function createGuidance(angle) {
  if (Math.abs(angle) <= 1) return { level: 'balanced', message: '현재 균형이 좋습니다.' };
  if (Math.abs(angle) <= 5) return { level: 'careful', message: angle > 0 ? '오른쪽을 조금 낮춰보세요.' : '왼쪽을 조금 낮춰보세요.' };
  return { level: 'adjust', message: angle > 0 ? '오른쪽을 많이 낮춰야 합니다.' : '왼쪽을 많이 낮춰야 합니다.' };
}
module.exports = { createGuidance };
```

```js
// server/src/routes/guidance.js
const express = require('express');
const { createGuidance } = require('../domain/guidance');
const router = express.Router();
router.post('/', (req, res) => {
  const { angle } = req.body || {};
  if (typeof angle !== 'number') return res.status(400).json({ code: 'INVALID_GUIDANCE_INPUT', message: 'angle(number) is required' });
  return res.json(createGuidance(angle));
});
module.exports = router;
```

```js
// server/src/app.js (append)
const guidanceRouter = require('./routes/guidance');
app.use('/api/v1/guidance', guidanceRouter);
app.use((req, res) => res.status(404).json({ code: 'NOT_FOUND', message: 'Route not found' }));
app.use((err, req, res, next) => res.status(500).json({ code: 'INTERNAL_SERVER_ERROR', message: err.message }));
```

- [ ] **Step 4: Run guidance tests and full server tests**

Run: `npm --prefix server test -- guidance.test.js`  
Run: `npm --prefix server test`  
Expected: PASS

- [ ] **Step 5: Commit**

```bash
git add server/src/domain/guidance.js server/src/routes/guidance.js server/src/app.js server/test/guidance.test.js
git commit -m "feat(server): add guidance endpoint and error handlers"
```

### Task 4: Updating Documentation and Regression Checks

**Files:**
- Modify: `README.md`
- Modify: `docs/getting-started.md`

- [ ] **Step 1: Add backend run section to README**

```markdown
## 백엔드 서버 실행

~~~bash
npm --prefix server install
npm --prefix server start
~~~

- Health Check: `http://localhost:8080/health`
```

- [ ] **Step 2: Add backend setup section to docs/getting-started.md**

```markdown
## 서버 개발 시작

1. Node.js 20 이상 설치
2. `npm --prefix server install`
3. `npm --prefix server test`
4. `npm --prefix server start`
```

- [ ] **Step 3: Run full regression checks**

Run: `npm --prefix server test`  
Run: `.\gradlew.bat testDebugUnitTest`  
Expected: both PASS

- [ ] **Step 4: Commit**

```bash
git add README.md docs/getting-started.md
git commit -m "docs: add backend server getting started"
```
