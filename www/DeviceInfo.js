var exec = require('cordova/exec');

module.exports.getImei = function(onSuccess, onError) {
   exec(onSuccess, onError, "DeviceInfo", "getImei", []);
};
module.exports.getImsi = function(onSuccess, onError) {
    exec(onSuccess, onError, "DeviceInfo", "getImsi", []);
 };
 module.exports.getIccid = function(onSuccess, onError) {
    exec(onSuccess, onError, "DeviceInfo", "getIccid", []);
 };
 module.exports.getMac = function(onSuccess, onError) {
    exec(onSuccess, onError, "DeviceInfo", "getMac", []);
 };
