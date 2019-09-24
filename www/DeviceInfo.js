var exec = require('cordova/exec');

module.exports.getImei = function (arg0, success, error) {
    exec(success, error, 'DeviceInfo', 'getImei', [arg0]);
}
module.exports.getMac = function (arg0, success, error) {
    exec(success, error, 'DeviceInfo', 'getMac', [arg0]);
}
module.exports.getUuid = function (arg0, success, error) {
    exec(success, error, 'DeviceInfo', 'getUuid', [arg0]);
}
