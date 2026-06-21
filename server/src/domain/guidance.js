function createGuidance(angle) {
  if (Math.abs(angle) <= 1) {
    return { level: 'balanced', message: '현재 균형이 좋습니다.' };
  }
  if (Math.abs(angle) <= 5) {
    return {
      level: 'careful',
      message: angle > 0 ? '오른쪽을 조금 낮춰보세요.' : '왼쪽을 조금 낮춰보세요.'
    };
  }
  return {
    level: 'adjust',
    message: angle > 0 ? '오른쪽을 많이 낮춰야 합니다.' : '왼쪽을 많이 낮춰야 합니다.'
  };
}

module.exports = {
  createGuidance
};
