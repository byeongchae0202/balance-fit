let latestCalibration = null;

function saveCalibration(data) {
  latestCalibration = {
    ...data,
    savedAt: new Date().toISOString()
  };
  return latestCalibration;
}

function getLatestCalibration() {
  return latestCalibration;
}

module.exports = {
  saveCalibration,
  getLatestCalibration
};
