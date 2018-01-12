'use strict';
angular.module('app', ['ngRoute', 'ngAnimate', 'angularMoment','ngCookies', 'angular-preload-image', 'truncate', 'app.routes', 'app.core', 'app.services', 'app.config'])
	   .controller('AppController',['$scope','$http','$window','$rootScope','$location', '$cookieStore', function($scope,$http,$window,$rootScope,$location,$cookieStore){
	   	
		 //alert('coooookie')
		 //alert($cookieStore.get('givenName'));
		   
		   function getCookieValue(a) {
			    var b = document.cookie.match('(^|;)\\s*' + a + '\\s*=\\s*([^;]+)');
			    return b ? b.pop() : '';
			}
		 
		   var userDetails = {}
		   //debugger;
		   userDetails.displayName = getCookieValue('displayName')
		   if(userDetails.displayName == null || userDetails.displayName == "" )
			   $window.location.href = '/misos-admin/secure/error.html';
		   //debugger;
		   userDetails.displayName  = userDetails.displayName.replace(/#/g, " ");
		   userDetails.givenName = getCookieValue('givenName')
		     userDetails.givenName  = userDetails.givenName.replace(/#/g, " ");
		   var auth  = getCookieValue('authorized')
		   if(auth == "true")
			   userDetails.authorized = true;
		   else
			   userDetails.authorized = false;
		   $scope.userDetails = userDetails
		   
	   	}]);


