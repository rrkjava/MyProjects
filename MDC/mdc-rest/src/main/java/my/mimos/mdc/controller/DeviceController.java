package my.mimos.mdc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import my.mimos.mdc.resources.BaseResponseResource;
import my.mimos.mdc.resources.device.AddDeviceRequestResource;
import my.mimos.mdc.resources.device.DeviceResponseResource;
import my.mimos.mdc.resources.device.DevicesResponseResource;
import my.mimos.mdc.resources.device.SearchDeviceRequestResource;
import my.mimos.mdc.resources.device.UpdateDeviceRequestResource;
import my.mimos.mdc.service.DeviceService;

@RestController
@RequestMapping(value="/")
public class DeviceController {
	
	@Autowired
	DeviceService deviceService;
	
	@RequestMapping(value = "/device/add", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<DeviceResponseResource> addDevice(@RequestBody AddDeviceRequestResource request) {
		DeviceResponseResource response = deviceService.addDevice(request);
		return new ResponseEntity<DeviceResponseResource>(response, HttpStatus.OK);		
	}
	
	@RequestMapping(value = "/device/update", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<DeviceResponseResource> updateDevice(@RequestBody UpdateDeviceRequestResource request) {
		DeviceResponseResource response = deviceService.updateDevice(request);
		return new ResponseEntity<DeviceResponseResource>(response, HttpStatus.OK);		
	}
	
	@RequestMapping(value = "/device/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<DeviceResponseResource> getDevice(@PathVariable("id") Long id) {
		DeviceResponseResource response = deviceService.getDevice(id);
		return new ResponseEntity<DeviceResponseResource>(response, HttpStatus.OK);		
	}
	
	@RequestMapping(value = "/device/list", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<DevicesResponseResource> listDevices() {
		DevicesResponseResource response = deviceService.listDevices();
		return new ResponseEntity<DevicesResponseResource>(response, HttpStatus.OK);		
	}
	
	@RequestMapping(value = "/device/search", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<DevicesResponseResource> searchDevices(SearchDeviceRequestResource searchCriteria) {
		DevicesResponseResource response = deviceService.searchDevices(searchCriteria);
		return new ResponseEntity<DevicesResponseResource>(response, HttpStatus.OK);		
	}
	
	@RequestMapping(value = "/device/remove/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<BaseResponseResource> removeDevice(@PathVariable("id") Long id) {
		BaseResponseResource response = deviceService.removeDevice(id);
		return new ResponseEntity<BaseResponseResource>(response, HttpStatus.OK);		
	}


}
