var util = require('../../utils/util.js');
Page({
  data: {
    //判断小程序的API，回调，参数，组件等是否在当前版本可用。
    canIUse: wx.canIUse('button.open-type.getUserInfo')
  },
  
  getUserInfo:function(){
    util.doGet('/f/demo/getUserInfo.do');
  },
  pay:function(){
    util.doPost("/f/demo/mpay.do",null,function(respone){
      var jsParams = respone.data;
      wx.requestPayment(
        {
          'timeStamp': jsParams.timeStamp,
          'nonceStr': jsParams.nonceStr,
          'package': jsParams._package,
          'signType': 'MD5',
          'paySign': jsParams.paySign,
          'success': function (res) {
            wx.showToast({
              title: '支付成功',
              duration: 2000
            });
          },
          'fail': function (res) {
            if (res.errMsg == 'requestPayment:fail cancel') {
              wx.showToast({
                title: '取消支付',
                duration: 2000
              });
            } else{
              wx.showToast({
                title: '支付失败' + res.errMsg,
                duration: 2000
              });
            }
          },
          'complete': function (res) { }
        })
    });
  },
  onLoad: function () {
    var $this = this;
    // 查看是否授权
    wx.getSetting({
      success: function (res) {
        if (res.authSetting['scope.userInfo']) {
          $this.setData ({ canIUse:false});
          wx.getUserInfo({
            success: function (res) {
              console.log(res.userInfo)
              //用户已经授权过
            }
          })
        } else {
          $this.setData({ canIUse: true });
        }
      }
    })
  },
  bindGetUserInfo:function(){
    console.log(e.detail.userInfo)
    if (e.detail.userInfo) {
      //用户按了允许授权按钮
    } else {
      //用户按了拒绝按钮
    }
  }
});