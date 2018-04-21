'use strict'
module.exports = (sequelize, DataTypes) => {
  var PasilistReview = sequelize.define('PasilistReview', {
    userId: {
      type: DataTypes.INTEGER,
      allowNull: false
    },
    speed: DataTypes.INTEGER,
    quality: DataTypes.INTEGER,
    humanNature: DataTypes.INTEGER
  }, {})
  PasilistReview.associate = (models) => {
    // associations can be defined here
  }
  return PasilistReview
}