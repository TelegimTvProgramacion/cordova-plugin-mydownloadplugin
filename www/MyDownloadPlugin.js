var MyDownloadPlugin = {
  downloadFile: function(fileUrl, fileName, successCallback, errorCallback) {
      cordova.exec(successCallback, errorCallback, "MyDownloadPlugin", "downloadFile", [fileUrl, fileName]);
  }
};

module.exports = MyDownloadPlugin;