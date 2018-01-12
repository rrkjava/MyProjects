localStorage.clear();
angular.module('app.core').controller('summaryController', function($scope, $http,baseUrl,$rootScope) {

	init();
	initVariables();

	//INITIALIZE DATE PICKERS
	function init() {

		$(function() {

			var start = moment().subtract(29, 'days');
			var end = moment();
			$scope.endDate = end.format('DD/MM/YYYY');
			$scope.startDate = start.format('DD/MM/YYYY');
			function cb(start, end) {
				$('#reportrangesummary span').html(start.format('D MMMM, YYYY') + ' - ' + end.format('D MMMM, YYYY'));
			}

			$('#reportrangesummary').daterangepicker({
				alwaysShowCalendars: true,
				startDate: start,
				endDate: end,
				opens: "right",
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

		$('#reportrangesummary').on('apply.daterangepicker', function(ev, picker) {
			var display = "[ "+ moment(picker.startDate).format('DD-MM-YYYY') +" TO "+ moment(picker.endDate).format('DD-MM-YYYY')+" ]";
			$scope.endDate = moment(picker.endDate).format('DD/MM/YYYY');
			$scope.startDate = moment(picker.startDate).format('DD/MM/YYYY');
		});

		//LIST CHANNEL TYPES AND SEVERITY LEVELS IN DROP DOWN
		$http.post(baseUrl+'/admin/channelTypes')
		.then(function(response) {
			// console.log(response);
			$scope.getChannelType = response.data.ListTargetChannelType;
		});

		defaultReportDisplay();

		$scope.getChannel = "";
		$scope.getStatus = "";
		
	}

	//VIEW REPORT 
	$scope.submit = function() {
		// debugger;
		data = {
				"channelType"   : $scope.getChannel,
				"status" : $scope.getStatus,
				"fromDate" : $scope.startDate,
				"toDate" : $scope.endDate
		}

		$http({
			url: baseUrl+'/admin/secure/summaryreport',
			method:"POST",
			data: data,
			headers:{
				'Accept': 'application/json',
				'Content-Type': 'application/json;charset=UTF-8'
			} 
		})
		.success(function (data, status, headers) {
			$scope.ReportList= data.summaryReportList;
		})
		.error(function (data, status, header) {
			//debugger;
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

	//DOWNLOAD REPORTS -PDF & DOCS
	$scope.downloadPDF = function() { 
		window.location.href = baseUrl+"/admin/summaryReport?fromDate="+$scope.startDate+"&toDate="+$scope.endDate+"&publishStatus="+$scope.getStatus+"&channelType="+$scope.getChannel+"&Accept-Language="+$scope.Language+"&fileFormat=PDF"      
	}

	$scope.downloadDOCX = function() {   
		window.location.href = baseUrl+"/admin/summaryReport?fromDate="+$scope.startDate+"&toDate="+$scope.endDate+"&publishStatus="+$scope.getStatus+"&channelType="+$scope.getChannel+"&Accept-Language="+$scope.Language+"&fileFormat=DOCX"    
	}
	
	$scope.downloadCSV = function() {   
		window.location.href = baseUrl+"/admin/summaryReport?fromDate="+$scope.startDate+"&toDate="+$scope.endDate+"&publishStatus="+$scope.getStatus+"&channelType="+$scope.getChannel+"&Accept-Language="+$scope.Language+"&fileFormat=CSV"    
	}

	function initVariables(){
		$scope.getStatus = "";
		$scope.getChannel = "";
		$scope.Language="en";
	}

	 console.log("value is"+ $scope.getChannel )

	$scope.publishStatus = [{
		id: true,
		status: 'Success'
	}, {
		id: false,
		status: 'Failure'
	}];

	$scope.getTotal = function(type) {
		var total = 0;
		angular.forEach($scope.ReportList, function(el) {
			total += el[type];
		});
		return total;
	};


	$scope.LangChange = function (s) {
        $scope.Language = s;
    };
	
	//DISPLAYS REPORT FOR LAST 30 DAYS BY  DEFAULT
	function defaultReportDisplay() {
		//debugger;
		var end = moment().format('DD/MM/YYYY');
		var start = moment().subtract(30, 'days').format('DD/MM/YYYY');
		var input1 = {
				"fromDate" : start, // input - last 30 days
				"toDate" : end 
		} 
		$http({
			url: baseUrl+'/admin/secure/summaryreport',
			method:"POST",
			data: input1,
			headers:{
				'Accept': 'application/json',
				'Content-Type': 'application/json;charset=UTF-8'
			} 
		})
		.success(function (response, status, headers) {
			$scope.ReportList= response.summaryReportList;
		});
	}


});