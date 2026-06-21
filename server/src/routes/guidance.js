const express = require('express');
const { createGuidance } = require('../domain/guidance');

const router = express.Router();

router.post('/', (req, res) => {
  const { angle } = req.body || {};

  if (typeof angle !== 'number') {
    return res.status(400).json({
      code: 'INVALID_GUIDANCE_INPUT',
      message: 'angle(number) is required'
    });
  }

  return res.json(createGuidance(angle));
});

module.exports = router;
