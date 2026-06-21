const express = require('express');
const healthRouter = require('./routes/health');
const calibrationRouter = require('./routes/calibration');

const app = express();

app.use(express.json());
app.use('/health', healthRouter);
app.use('/api/v1/calibration', calibrationRouter);

module.exports = app;
