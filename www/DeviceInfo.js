var exec = require('cordova/exec');

module.exports.getImei = function (arg0, success, error) {
    exec(success, error, 'DeviceInfo', 'getImei', [arg0]);
}
module.exports.getImsi = function (arg0, success, error) {
    exec(success, error, 'DeviceInfo', 'getImsi', [arg0]);
}
module.exports.getIccid = function (arg0, success, error) {
    exec(success, error, 'DeviceInfo', 'getImsi', [arg0]);
}
