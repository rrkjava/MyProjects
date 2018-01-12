localStorage.clear();
angular.module('app.core').controller('AgencyController', function($scope,$http,baseUrl,$rootScope,$route) {


   $http.get(baseUrl+'/mobilegateway/admin/secure/listusergroups').
   then(function(response){
        $scope.agencies= response.data.agencies; 
    });

   $scope.selectedAgency = { selected: {} };

    // gets the template to ng-include for a table row / item
    $scope.getTemplate = function (agency) {
        if (agency.agencyId === $scope.selectedAgency.selected.agencyId) 
            return 'edit';
        else 
            return 'display';
    };

    $scope.editAgency = function (agency) {
        $scope.selectedAgency.selected = angular.copy(agency);
    };

    $scope.crateAgency = function () {
        var input = {
          "agency": $scope.getAgencyName
        }
        $http({
            url: baseUrl+'/mobilegateway/admin/secure/createagency',
            method:"POST",
            data: input,
            headers:{
              'Accept': 'application/json',
              'Content-Type': 'application/json;charset=UTF-8'
            } 
        })
        .success(function (response, status, headers) {
            // sucess reset domain
            if( response.statusType == "SUCCESS"){
                $route.reload();
                // console.log("domain successfully created");
            } else {

            }
        })
        .error(function (data, status, header) {
            console.log("error in fetching data");
        });
    };

    $scope.removeAgency = function (idx) {
        var input = {
          "agencyId": $scope.agencies[idx].agencyId,
        }
        $http({
            url: baseUrl+'/mobilegateway/admin/secure/deleteagency',
            method:"POST",
            data: input,
            headers:{
              'Accept': 'application/json',
              'Content-Type': 'application/json;charset=UTF-8'
            } 
        })
        .success(function (response, status, headers) {
            // sucess reset domain
            if( response.statusType == "SUCCESS"){
               $scope.agencies.splice(idx, 1); 
               // console.log("delete successfully");
            } else {

            }
        })
        .error(function (data, status, header) {
            console.log("error in fetching data");
        });
        
    };


    $scope.saveAgency = function (idx) {
        var input = {
          "agencyId": $scope.selectedAgency.selected.agencyId,
          "agency"  : $scope.selectedAgency.selected.agency
        }
        $http({
            url: baseUrl+'/mobilegateway/admin/secure/updateagency',
            method:"POST",
            data: input,
            headers:{
              'Accept': 'application/json',
              'Content-Type': 'application/json;charset=UTF-8'
            } 
        })
        .success(function (response, status, headers) {
            // sucess reset domain
            if( response.statusType == "SUCCESS"){
              $scope.agencies[idx] = angular.copy($scope.selectedAgency.selected);
              $scope.reset();   
            } else {

            }
        })
        .error(function (data, status, header) {
            console.log("error in fetching data");
        });
        
    };

    $scope.reset = function () {
        $scope.selectedAgency.selected = {};
    };



    $http.get(baseUrl+'/mobilegateway/admin/secure/displaydomains').
   then(function(response){
        $scope.domains= response.data.emaildomains; 
    });

   $scope.selectedDomain = { selected: {} };

    // gets the template to ng-include for a table row / item
    $scope.getDomainTemplate = function (domain) {
        if (domain.domainId === $scope.selectedDomain.selected.domainId) 
            return 'domainEdit';
        else 
            return 'domainDisplay';
    };

    

    $scope.crateDomain = function () {
        var input = {
          "agencyId": $scope.getAgency,
          "domain"  : $scope.getDomain
        }
        $http({
            url: baseUrl+'/mobilegateway/admin/secure/createdomain',
            method:"POST",
            data: input,
            headers:{
              'Accept': 'application/json',
              'Content-Type': 'application/json;charset=UTF-8'
            } 
        })
        .success(function (response, status, headers) {
            // sucess reset domain
            if( response.statusType == "SUCCESS"){
                $route.reload();
                // console.log("domain successfully created");
            } else {

            }
        })
        .error(function (data, status, header) {
            console.log("error in fetching data");
        });
    };

    $scope.editDomain = function (domain) {
        $scope.selectedDomain.selected = angular.copy(domain);
    };

    $scope.removeDomain = function (idx) {

        var input = {
          "domainId": $scope.domains[idx].domainId,
        }
        $http({
            url: baseUrl+'/mobilegateway/admin/secure/deletedomain',
            method:"POST",
            data: input,
            headers:{
              'Accept': 'application/json',
              'Content-Type': 'application/json;charset=UTF-8'
            } 
        })
        .success(function (response, status, headers) {
            // sucess reset domain
            if( response.statusType == "SUCCESS"){
               $scope.domains.splice(idx, 1); 
               console.log("delete successfully");
            } else {

            }
        })
        .error(function (data, status, header) {
            console.log("error in fetching data");
        });
    };


    $scope.saveDomain = function (idx) {
        var input = {
          "domainId": $scope.selectedDomain.selected.domainId,
          "domain"  : $scope.selectedDomain.selected.emailDomain
        }
        $http({
            url: baseUrl+'/mobilegateway/admin/secure/updatedomain',
            method:"POST",
            data: input,
            headers:{
              'Accept': 'application/json',
              'Content-Type': 'application/json;charset=UTF-8'
            } 
        })
        .success(function (response, status, headers) {
            // sucess reset domain
            if( response.statusType == "SUCCESS"){
              $scope.domains[idx] = angular.copy($scope.selectedDomain.selected);
              $scope.resetDomain();    
            } else {

            }
        })
        .error(function (data, status, header) {
            console.log("error in fetching data");
        });
        
    };

    $scope.resetDomain = function () {
        $scope.selectedDomain.selected = {};
    };

    // Loader
    $rootScope.$on('loading:progress', function (){
        // console.log("loading");
        $scope.loading = true;
    });

    $rootScope.$on('loading:finish', function (){
            $scope.loading = false;
            // console.log("stop");
    });

}).directive('tooltip', function(){
    return {
        restrict: 'A',
        link: function(scope, element, attrs){
            $(element).hover(function(){
                // on mouseenter
                $(element).tooltip('show');
            }, function(){
                // on mouseleave
                $(element).tooltip('hide');
            });
        }
    };
});