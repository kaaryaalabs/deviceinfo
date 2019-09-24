var exec = require('cordova/exec');

var PLUGIN_NAME = "DeviceInfo"; // This is just for code completion uses.

var DeviceInfo = function() {}; 
DeviceInfo.getImei = function(onSuccess, onError) {
   exec(onSuccess, onError, PLUGIN_NAME, "getImei", []);
};
DeviceInfo.getImsi = function(onSuccess, onError) {
    exec(onSuccess, onError, PLUGIN_NAME, "getImsi", []);
 };
 DeviceInfo.getIccid = function(onSuccess, onError) {
    exec(onSuccess, onError, PLUGIN_NAME, "getIccid", []);
 };
 DeviceInfo.getMac = function(onSuccess, onError) {
    exec(onSuccess, onError, PLUGIN_NAME, "getMac", []);
 };
module.exports = DeviceInfo;
