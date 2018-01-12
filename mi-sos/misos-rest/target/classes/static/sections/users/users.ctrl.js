'use strict';

angular
    .module('app.core')
    .controller('UsersController',function($scope,$http,baseUrl,$rootScope) {

    //ON LOAD
	$scope.$on('$viewContentLoaded', function(){
	    initDatePickers();
    	listUsers();
    	listUserGroups();
    	init();
	  })

	//INITIALIZE VARIABLES
	function initValues() {
		$scope.toDate = "" ;
		$scope.fromDate = ""
		$scope.userGroupType = ""
		$scope.userGroup.agency =""
		/*var status = [];
		var option = {value:0, label:"inactive"};
		status.push(option)*/
	}
    	

	//LIST INTERNAL USERS
	function listUsers(){
		 $http.get(baseUrl+'/mobilegateway/admin/secure/displayInternalUsers').
		 then(function(response){
            //data from web service
           	$scope.users= response.data.internalUsers;
            //sorting 
           /* $scope.sort = function(keyname){
                $scope.sortKey = keyname;   //set the sortKey to the param passed
                $scope.reverse = !$scope.reverse; //if true make it false and vice versa
            }; */
        });
	}

	//INITIALIZE DATE PICKERS    	
	function initDatePickers(){
		$('#fromDateCal').daterangepicker({
			singleDatePicker: true,
			singleClasses: "picker_2"
			}, 
			function(start, end, label) {
			$scope.fromDate=start.format('YYYY-MM-DD');
			
		});

		$('#toDateCal').daterangepicker({
			singleDatePicker: true,
			singleClasses: "picker_2"
			}, 
			function(start, end, label) {
			
			$scope.toDate=start.format('YYYY-MM-DD');
			console.log($scope.toDate)
		});
   	} 


	//SEARCH INTERNAL USERS
  	$scope.searchUsers  =function(){

  	var userGroupInput = "";  	
  	 if(angular.isUndefined( $scope.userGroup) ||  $scope.userGroup == null) {
  	 	userGroupInput = "";
  	 }else{
  	 	userGroupInput = $scope.userGroup.agency ;
  	 }

	var data = {				
			"fromDate"    		: $scope.startDate,
			"toDate"      		: $scope.endDate,
			"userGroupType"  	: userGroupInput,
			"status"  	  		: $scope.status
		}
		$http({
			url:baseUrl+'/mobilegateway/admin/secure/searchinternalusers',
			method:"POST",
			data: data,
			headers:{
			'Accept': 'application/json',
			'Content-Type': 'application/json;charset=UTF-8'
			} 
		})
		.success(function (data, status, headers) {
			$scope.users= data.internalUsers;
		})
		.error(function (data, status, header) {
		    console.log("error in fetching data");
		});
	}

	//CHANGE ACCESS
	$scope.changeAcess = function(tokenId,status){
		debugger;
		var sts ="inactive";
		if(status == "1"){
			sts="active";
		}
		debugger;
		var input = {
			"tokenId" : tokenId,
			"status"  : sts,
		}
		$http({
			url:baseUrl+'/mobilegateway/admin/secure/changeAccessRights',
			method:"POST",
			data: input,
			headers:{
			'Accept': 'application/json',
			'Content-Type': 'application/json;charset=UTF-8'
			} 
		})		
		.error(function (data, status, header) {
		    console.log("error in changing access");
		});

	}

	//FILL USER GROUP DROP DOWN
	function listUserGroups(){
		 $http.get(baseUrl+'/mobilegateway/admin/secure/listusergroups').
		 then(function(response){
            //data from web service
           	$scope.agencies= response.data.agencies;            
        });
	}

	// Loader
    $rootScope.$on('loading:progress', function (){
        // console.log("loading");
        $scope.loading = true;
    });

    $rootScope.$on('loading:finish', function (){
            $scope.loading = false;
            // console.log("stop");
    });

	//DATE RANGE PICKER
	function init() {

    $(function() {

    var start = moment().subtract(29, 'days');
    var end = moment();

	    function cb(start, end) {
	        $('#userdaterange span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));
	    }

	    $('#userdaterange').daterangepicker({
	        alwaysShowCalendars: true,
	        startDate: start,
	        endDate: end,
	        opens: "right",
        	drops: "up",
	        ranges: {
	           'Today': [moment(), moment()],
	           'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
	           'Last 7 Days': [moment().subtract(6, 'days'), moment()],
	           'Last 30 Days': [moment().subtract(29, 'days'), moment()],
	           'This Month': [moment().startOf('month'), moment().endOf('month')],
	           'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
	        }
	    }, cb);
	    cb(start, end);
    
  });

	    $('#userdaterange').on('apply.daterangepicker', function(ev, picker) {
	        var display = "[ "+ moment(picker.startDate).format('DD-MM-YYYY') +" TO "+ moment(picker.endDate).format('DD-MM-YYYY')+" ]";
	        $scope.endDate = moment(picker.endDate).format('YYYY-MM-DD');
	        $scope.startDate = moment(picker.startDate).format('YYYY-MM-DD');
	        //alert(display)
	    });


	}

	
 });