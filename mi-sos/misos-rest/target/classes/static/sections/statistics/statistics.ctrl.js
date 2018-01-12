localStorage.clear();
angular.module('app.core').controller('StatisticsController', function($scope, $http,baseUrl,$rootScope) {

	init();
	initVariables();

	//INITIALIZE DATE PICKER
	function init() {

		$(function() {

			var start = moment().subtract(29, 'days');
			var end = moment();
			$scope.endDate = end.format('DD/MM/YYYY');
			$scope.startDate = start.format('DD/MM/YYYY');
			function cb(start, end) {
				$('#reportrange span').html(start.format('D MMMM, YYYY') + ' - ' + end.format('D MMMM, YYYY'));
			}

			$('#reportrange').daterangepicker({
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

		$('#reportrange').on('apply.daterangepicker', function(ev, picker) {
			var display = "[ "+ moment(picker.startDate).format('DD-MM-YYYY') +" TO "+ moment(picker.endDate).format('DD-MM-YYYY')+" ]";
			$scope.endDate = moment(picker.endDate).format('DD/MM/YYYY');
			$scope.startDate = moment(picker.startDate).format('DD/MM/YYYY');
		});

		defaultReportDisplay();
	}

	//LIST CHANNEL TYPES AND SEVERITY LEVELS IN DROP DOWN
	$http.post(baseUrl+'/admin/channelTypes')
	.then(function(response) {
		// console.log(response);
		$scope.getChannelType = response.data.ListTargetChannelType;
	});

	$http.post(baseUrl+'/admin/severityTypes')
	.then(function(response) {
		// console.log(response);
		$scope.getSeverityLevel = response.data.ListSeverityLevel;
	});



	//VIEW REPORT 
	$scope.submit = function() {
		//debugger;
		data = {
				"channelType"   : $scope.getChannel,
				"severityLevel" : $scope.getSeverity,
				"fromDate" : $scope.startDate,
				"toDate" : $scope.endDate
		}

		$http({
			url: baseUrl+'/admin/secure/auditdetailreport',
			method:"POST",
			data: data,
			headers:{
				'Accept': 'application/json',
				'Content-Type': 'application/json;charset=UTF-8'
			} 
		})
		.success(function (data, status, headers) {

			$scope.Channelmessages= data.channelMessageList;
			// console.log(data)
			$scope.sort = function(keyname){
				$scope.sortKey = keyname;  
				$scope.reverse = !$scope.reverse; 
			};          
		})
		.error(function (data, status, header) {
			//debugger;
		});
	}
	
	$scope.currentPage = 1; 
	$scope.pageSize = 8; 

	// Loader
	$rootScope.$on('loading:progress', function (){
		// console.log("loading");
		$scope.loading = true;
	});

	$rootScope.$on('loading:finish', function (){
		$scope.loading = false;
		// console.log("stop");
	});
	
	//DOWNLOAD REPORTS PDF & DOCS
	$scope.downloadAuditReportPDF = function() {   
		window.location.href = baseUrl+"/admin/reportPDF?fromDate="+$scope.startDate+"&toDate="+$scope.endDate+"&severityLevel="+$scope.getSeverity+"&channelType="+$scope.getChannel+"&Accept-Language="+$scope.Language+"&fileFormat=PDF"
	}

	$scope.downloadAuditReportDOCX = function() {   
		window.location.href = baseUrl+"/admin/reportPDF?fromDate="+$scope.startDate+"&toDate="+$scope.endDate+"&severityLevel="+$scope.getSeverity+"&channelType="+$scope.getChannel+"&Accept-Language="+$scope.Language+"&fileFormat=DOCX"
	}
	
	$scope.downloadAuditReportCSV = function() {   
		window.location.href = baseUrl+"/admin/reportPDF?fromDate="+$scope.startDate+"&toDate="+$scope.endDate+"&severityLevel="+$scope.getSeverity+"&channelType="+$scope.getChannel+"&Accept-Language="+$scope.Language+"&fileFormat=CSV"
	}

	function initVariables(){
		$scope.getSeverity = "";
		$scope.getChannel = "" ;
		$scope.Language="en";
	}

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
			url: baseUrl+'/admin/secure/auditdetailreport',
			method:"POST",
			data: input1,
			headers:{
				'Accept': 'application/json',
				'Content-Type': 'application/json;charset=UTF-8'
			} 
		})
		.success(function (response, status, headers) {
			$scope.Channelmessages= response.channelMessageList;
		});
	}


});