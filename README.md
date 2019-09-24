Hi,

This plugin is developed by Kaaryaa Labs. 

You can use this plugin by 

installing the plugin using following command

    ionic cordova plugin add https://github.com/kaaryalabs/deviceinfo.git

then you have to declare a variable 'DeviceInfo' under import on any ts file you want to access it to.

    import { Component, OnInit } from '@angular/core';
    declare var DeviceInfo;

Then you can call following methods

then call method 

     DeviceInfo.getImei('', (imei) => {
          console.log('imei is: ', imei);
        }, error => console.log('error imei is: ', error));

Thanks
