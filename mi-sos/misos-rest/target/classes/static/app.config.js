'use strict';
angular
    .module('app.config', [])
    .config(configs)
    .run(runs)
//    .constant('baseUrl',''); // MIMOS
.constant('baseUrl','http://localhost:8080/');
	// .constant('baseUrl','https://11.1.32.40:8443/misos');  //MUTIARA
    //.constant('baseUrl','https://11.1.2.129:8443/misos');  //PDSA - 1
	//.constant('baseUrl','https://11.1.2.136:8443/misos');  //PDSA - 2 (PRIMARY)
//    .constant('baseUrl','https://dissemination.water.gov.my/misos'); // PDSA domain

function configs($httpProvider) {
    var interceptor = function($location, $log, $q) {
       // debugger;
        function error(response) {
            if (response.status === 401) {
                $log.error('You are unauthorised to access the requested resource (401)');
            } else if (response.status === 404) {
                $log.error('The requested resource could not be found (404)');
            } else if (response.status === 500) {
                $log.error('Internal server error (500)');
            }
            return $q.reject(response);
        }
        function success(response) {
            //Request completed successfully
            return response;
        }
        return function(promise) {
            return promise.then(success, error);
        }
    };
    $httpProvider.interceptors.push(interceptor);
}


function runs($rootScope, $location) {
    $rootScope.$on('$routeChangeStart',function(event, next, current) {

     
    });
    $rootScope.$on('$routeChangeSuccess', function() {
       // PageValues.loading = false;
    });

}
