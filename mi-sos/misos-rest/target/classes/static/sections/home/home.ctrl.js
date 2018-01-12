'use strict';
     angular
    .module('app.core')
    .controller('HomeController',function($scope, $http,baseUrl){
 		//LIST OF MESSAGES  
       $http.post(baseUrl+'/mobilegateway/admin/secure/recentDissemination').
        then(function(response){
            //data from web service
            $scope.recentDissemination= response.data.iocMessages;            
        });

        $http.post(baseUrl+'/mobilegateway/admin/secure/recentUserActivity').
        then(function(response){
            //data from web service
            $scope.recentUserActivity= response.data.internalUsers;            
        });

        // Loader
        $rootScope.$on('loading:progress', function (){
            // console.log("loading");
            $scope.loading = true;
        });

        $rootScope.$on('loading:finish', function (){
                $scope.loading = false;
                // console.log("stop");
        });
        
        
    });
