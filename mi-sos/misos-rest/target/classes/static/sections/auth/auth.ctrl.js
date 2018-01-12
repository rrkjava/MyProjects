'use strict';

angular
    .module('app.core')
    .controller('AuthController',function($scope,$http,baseUrl,$rootScope,$window) {
	   		
	$scope.uap="";

	$scope.$on('$viewContentLoaded', function(){

    $rootScope.pid=angular.element(document.getElementById('auth')).val()
	//alert($rootScope.pid)

	 queryActiveDirectory();
	   		//debugger;
	        //QUERY AD FOR USER DETAILS  
		  	function queryActiveDirectory(){
				var data = {				
					"uapPID" : $rootScope.pid	// needs to be uncommented when our pid added in mutiara AD
					//"uapPID" : "ARyM8-vWheG4c6XX1O5p7_ccTfDyJqlKOhMGx_qk"	//sample uid for testing			
				}
				$http({
					url:'https://11.1.32.41:9999/ad-service/search', // needs to changed to https /does not work with local host or http 
					method:"POST",
					data: data,
					/*headers:{
						//'Accept': 'application/json',
						'Content-Type': 'application/json'
					} */
				})
				.success(function (data, status, headers) {
					//console.log(data);
					$rootScope.userDetails= data.result.adUserData; 
					if($rootScope.userDetails.misosRoles == 'Admin'){ // check if role is admin
						//console.log("authorized : "+ $scope.userDetails.misosRoles + ", name : " + $scope.userDetails.givenname);
						$rootScope.authenticated=true;// authencation success - proceed to home page
					}else{
						$rootScope.authenticated=false;
						$window.location.href = '/mobile-admin/secure/error.html';/// authentication fail - show error page
					}
					checkIfAuthorized();
				})
				.error(function (data, status, header) {
				    console.log("error in fetching user data from AD");
				    $rootScope.authenticated=false;
				    checkIfAuthorized();
				});
			}

			function checkIfAuthorized(){						
				if($rootScope.authenticated == false){
					$window.location.href = '/mobile-admin/secure/error.html';// authentication fail - show error page
				}
			}
	   		
	if($rootScope.redirectLocation1 != null && $rootScope.redirectLocation1.length > 1){
		$window.location.href = "#"+$rootScope.redirectLocation1;
	}else{
		$window.location.href = "#/home"; //redirect to home page if authorized
	}

  });
});