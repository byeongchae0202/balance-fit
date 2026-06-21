const request = require('supertest');
const app = require('../src/app');

describe('Guidance API', () => {
  it('returns guidance for angle', async () => {
    const res = await request(app).post('/api/v1/guidance').send({ angle: 6 });
    expect(res.statusCode).toBe(200);
    expect(res.body.level).toBe('adjust');
  });
});
