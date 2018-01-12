'use strict';
     
angular.module('app.core')
       .controller('DashboardController',function($scope, $http,baseUrl,$rootScope){

  //ON LOAD
	$scope.$on('$viewContentLoaded', function(){	
		getDataForDisplay();
	});

	//global variables
	var sms_pass = 0 ;
	var sms_fail  = 0;
	var email_pass  = 0;
	var email_fail = 0;
	var mob_pass = 0;
	var mob_fail = 0;
	var fb_pass = 0;
	var fb_fail = 0;
	var tweet_pass = 0;
	var tweet_fail = 0;  

	$scope.total_sms = 0;
	$scope.total_email =0;
	$scope.total_tweet = 0;
	$scope.total_mobile = 0;
	$scope.total_fb = 0;
	
	var portal_pass = 0;
	var portal_fail = 0;  
	var fax_pass = 0;
	var fax_fail = 0;  
	var siren_pass = 0;
	var siren_fail = 0;  
	
	$scope.total_portal = 0;
	$scope.total_fax = 0;
	$scope.total_siren = 0;
	

	//test
	var end = moment().format('DD/MM/YYYY');
	var start = moment().startOf('year').format('DD/MM/YYYY');
	var startYear = moment().startOf('year').format('YYYY');
	var endYear = moment().format('YYYY');
  

	$http.post(baseUrl+'/admin/recentDissemination').
        then(function(response){
            //data from web service
            $scope.recentDissemination= response.data.channelMessageList;            
    });


	//CALL WEBSERVICES
	function getDataForDisplay() {

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

		var lengthOfList =response.summaryReportList.length;     	

      	for (var i=0; i<lengthOfList; i++) {
      		var channel = response.summaryReportList[i].channelType;
      		var publish = response.summaryReportList[i].publishStatus;
      		var total = response.summaryReportList[i].numOfMessages;

      		if(channel === "SMS"){
      			if(publish){
      				//console.log(" Failure sms : "+ total);
      				sms_pass = total;
      			}else{
      				//console.log(" Success SMS: "+ total);
      				sms_fail = total;
      			}			
      		}else if(channel === "Email"){
      			if(publish){
      				//console.log(" Failure email : "+ total);
      				email_pass = total;
      			}else{
      				//console.log(" Success email: "+ total);
      				email_fail = total;
      			}
      		}else if(channel === "Facebook"){
      			if(publish){
      				//console.log(" Failure fb : "+ total);
      				fb_pass = total;
      			}else{
      				console.log(" Success fb : "+ total);
      				fb_fail = total;
      			}      			
      		}else if(channel === "Twitter"){
      			if(publish){
      				//console.log(" Failure tweet : "+ total);
      				tweet_pass = total;
      			}else{
      				//console.log(" Success tweet : "+ total);
      				tweet_fail = total;
      			}      			
      		}else if(channel === "Mobile App"){
      			if(publish){
      				//console.log(" Failure mobile : "+ total);
      				mob_pass = total;
      			}else{
      				//console.log(" Success mobile : "+ total);
      				mob_fail = total;
      			}  
      		}else if(channel === "Siren"){
      			if(publish){
      				//console.log(" Failure siren : "+ total);
      				siren_pass = total;
      			}else{
      				//console.log(" Success siren : "+ total);
      				siren_fail = total;
      			}  
      		}else if(channel === "Fax"){
      			if(publish){
      				//console.log(" Failure fax : "+ total);
      				fax_pass = total;
      			}else{
      				//console.log(" Success fax : "+ total);
      			    fax_fail = total;
      			}  
      		}else if(channel === "Public Portal"){
      			if(publish){
      				//console.log(" Failure portal : "+ total);
      				portal_pass = total;
      			}else{
      				//console.log(" Success portal : "+ total);
      				portal_fail = total;
      			}  
      		}
      		
	    }

	    //compute data for total
	    	$scope.total_sms = sms_pass + sms_fail;
	    	$scope.total_email = email_pass + email_fail;
	    	$scope.total_tweet = tweet_pass + tweet_fail;
	    	$scope.total_mobile = mob_pass + mob_fail ;
 			$scope.total_fb = fb_pass + fb_fail ;	
 			
 			$scope.total_portal = portal_pass + portal_fail;
	    	$scope.total_siren = siren_pass + siren_fail ;
 			$scope.total_fax = fax_pass + fax_fail ;	

 		//display bar chart 			
	    	displayBarchat();

      })
      .error(function (response1, status, header) {
        
      });
    }


function displayBarchat() {


    Highcharts.chart('barChartDiv', {
        chart: {
            type: 'bar'
        },
        title: {
            text: 'Dissemination for flood warning'
        },
        subtitle: {
            text: 'JPS FDC - ' + startYear 
        },
        xAxis: {
            categories: ['SMS', 'Email', 'Mobile', 'Facebook', 'Twitter','Siren','Fax','Portal'],
            title: {
                text: null
            }
        },
        yAxis: {
            min: 0,
            title: {
                text: 'Number of Messages Disseminated',
                align: 'high'
            },
            labels: {
                overflow: 'justify'
            }
        },
        tooltip: {
            valueSuffix: 'messages'
        },
        plotOptions: {
            bar: {
                dataLabels: {
                    enabled: true
                }
            }
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'top',
            // x: -40,
           // y: 80,
            floating: true,
            borderWidth: 1,
            backgroundColor: ((Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'),
            shadow: true
        },
        credits: {
            enabled: false
        },
        series: [{
            name: 'Sucess',
            data: [sms_pass, email_pass, mob_pass, fb_pass, tweet_pass, siren_pass, fax_pass, portal_pass]
        },
        {
            name: 'Failure',
            data: [sms_fail, email_fail, mob_fail, fb_fail, tweet_fail, siren_fail, fax_fail, portal_fail]
        },
        {
            name: 'Total',
            data: [sms_pass+sms_fail, email_pass + email_fail, mob_pass+mob_fail, fb_pass+fb_fail, tweet_pass+tweet_fail, siren_pass+siren_fail,fax_pass+fax_fail, portal_pass+portal_fail]
        }]
    });
}





	
});