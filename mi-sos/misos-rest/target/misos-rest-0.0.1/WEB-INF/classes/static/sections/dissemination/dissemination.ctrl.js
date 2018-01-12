'use strict';

  angular
    .module('app.core')
    .controller('DisseminationController',['$scope', '$http','baseUrl','$rootScope',function($scope, $http,baseUrl,$rootScope) {


      $scope.$on('$viewContentLoaded', function(){
        listMessages();
        listChannels();
        listSeverity();
        init();
      })

      //INITIALIZE VARIABLES
      function initValues() {       
      }

      //LIST OF MESSAGES  
      function listMessages(){        
        $http.get(baseUrl+'/mobilegateway/admin/secure/displayIocMessages').
          then(function(response){
              //data from web service
              $scope.messages= response.data.iocMessages;
              //sorting 
              $scope.sort = function(keyname){
                  $scope.sortKey = keyname;   //set the sortKey to the param passed
                  $scope.reverse = !$scope.reverse; //if true make it false and vice versa
              }; 
          });
      }

      //LIST CHANNELS IN DROP DOWN
      function listChannels(){
         $http.get(baseUrl+"/mobilegateway/masterdata/channels")
         .then(function(response) {
            $scope.getChannelType = response.data.channelList;
          });
      }

      //LIST SEVERITY
      function listSeverity(){
         $http.get(baseUrl+'/mobilegateway/masterdata/severitylevels')
         .then(function(response) {
          $scope.getSeverityLevel = response.data.severityLevel;
        });
      }

      //ADVANCED SEARCH
      $scope.submit = function() {
        var input = {
          "channelType"   : $scope.getChannel,
          "severityLevel" : $scope.getSeverity,
          "fromDate" : $scope.startDate,
          "toDate" : $scope.endDate
        }

        $http({
            url:baseUrl+'/mobilegateway/admin/secure/searchchannelmessages',
            method:"POST",
            data: input,
            headers:{
              'Accept': 'application/json',
              'Content-Type': 'application/json;charset=UTF-8'
            } 
        })
        .success(function (response, status, headers) {
            //debugger;
            $scope.messages= response.channelMessageList;     
            //sorting 
            $scope.sort = function(keyname){
                $scope.sortKey = keyname;   //set the sortKey to the param passed
                $scope.reverse = !$scope.reverse; //if true make it false and vice versa
            };               
        })
        .error(function (data, status, header) {
            console.log("error in fetching data");
        });
    } 

    //DATE RANGE PICKER
  function init() {

    $(function() {

    var start = moment().subtract(29, 'days');
    var end = moment();

      function cb(start, end) {
          $('#reportrange span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));
      }

      $('#reportrange').daterangepicker({
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

      $('#reportrange').on('apply.daterangepicker', function(ev, picker) {
          var display = "[ "+ moment(picker.startDate).format('DD-MM-YYYY') +" TO "+ moment(picker.endDate).format('DD-MM-YYYY')+" ]";
          $scope.endDate = moment(picker.endDate).format('YYYY-MM-DD');
          $scope.startDate = moment(picker.startDate).format('YYYY-MM-DD');
          //alert(display)
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


   }]);

 

   
     

