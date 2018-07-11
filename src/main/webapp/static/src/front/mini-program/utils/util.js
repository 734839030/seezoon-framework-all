var app = getApp();
var urlPreffix = app.globalData.url;

/**
 * 后台的
 */
function getSessionId(){
  var seesionId = wx.getStorage({
    key: 'fseezoon-sid'
  });
  return seesionId ? seesionId : '';
}

/**
 * 小程序的
 */
function getSessionKey(){
  var sessionKey = wx.getStorage({
    key: 'session_key'
  });
  return sessionKey ? sessionKey : '';
}
/**
 * 全局发送请求方法
 */
function send(options, successCallback, failCallback) {
  options.url = urlPreffix + options.url;
  var defaults = {
    dataType: 'json',
    header: {
      'content-type': 'application/x-www-form-urlencoded',
      'Cookie': 'fseezoon-sid=' + getSessionId()
    },
    success: function (respone) {
      var statusCode = respone.statusCode;
      if (statusCode == 310) {
        login();
        //未登录
        wx.showToast({
          title: '未登录',
          duration: 2000
        });
      } else if (statusCode == 200) {
        callback(respone.data);
      } else {
        wx.showToast({
          title: '请求状态异常:' + statusCode,
          duration: 2000
        });
      }
    },
    fail: function () {
      if (!failCallback) {
        wx.showToast({
          title: '请求失败',
          duration: 2000
        });
      }
    }
  };
  var params = Object.assign({}, defaults, options);
  wx.request(params);
}
/**
 * get 请求
 */
function doGet(url, data, successCallback) {
  var options = {};
  options.method = 'GET';
  options.url = url;
  options.data = data;
  send(options, successCallback);
}

/**
 * post 请求
 */
function doPost(url, data, successCallback) {
  var options = {};
  options.method = 'POST';
  options.url = url;
  options.data = data;
  send(options, successCallback);
}

function login() {
  wx.login({
    success: function (res) {
      if (res.code) {
        //发起网络请求
        doPost('/public/wechat/mauth2Login.do', {code: res.code},function(respone){
          wx.setStorage({
            key: "fseezoon-sid",
            data: respone.seezoonSessionKey
          });
          wx.setStorage({
            key: "session_key",
            data: respone.session_key
          })
        });
      } else {
        console.log('登录失败！' + res.errMsg)
      }
    }
  });
}

//模块化
module.exports = {
  send: send,
  doGet: doGet,
  doPost: doPost
}