const express = require('express');
const healthRouter = require('./routes/health');
const calibrationRouter = require('./routes/calibration');
const guidanceRouter = require('./routes/guidance');

const app = express();

app.use(express.json());
app.use('/health', healthRouter);
app.use('/api/v1/calibration', calibrationRouter);
app.use('/api/v1/guidance', guidanceRouter);

app.use((req, res) => {
  res.status(404).json({
    code: 'NOT_FOUND',
    message: 'Route not found'
  });
});

app.use((err, req, res, next) => {
  res.status(500).json({
    code: 'INTERNAL_SERVER_ERROR',
    message: err.message
  });
});

module.exports = app;
