const express = require('express');
const { saveCalibration, getLatestCalibration } = require('../store/calibrationStore');

const router = express.Router();

router.post('/', (req, res) => {
  const { offsetX, offsetY, profile } = req.body || {};

  if (typeof offsetX !== 'number' || typeof offsetY !== 'number' || typeof profile !== 'string') {
    return res.status(400).json({
      code: 'INVALID_CALIBRATION_INPUT',
      message: 'offsetX, offsetY(number), profile(string) are required'
    });
  }

  const calibration = saveCalibration({ offsetX, offsetY, profile });
  return res.status(201).json({ saved: true, calibration });
});

router.get('/latest', (req, res) => {
  const latest = getLatestCalibration();
  if (!latest) {
    return res.status(404).json({
      code: 'CALIBRATION_NOT_FOUND',
      message: 'No calibration data'
    });
  }
  return res.json(latest);
});

module.exports = router;
