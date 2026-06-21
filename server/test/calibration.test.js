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
    await request(app).post('/api/v1/calibration').send({ offsetX: 0.1, offsetY: 0.2, profile: 'outdoor' });
    const res = await request(app).get('/api/v1/calibration/latest');
    expect(res.statusCode).toBe(200);
    expect(res.body.profile).toBe('outdoor');
  });
});
