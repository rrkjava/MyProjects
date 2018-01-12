'use strict';
 
angular.module('app.core').factory('TemplateService', ['$http', '$q', function($http, $q){
 
    var REST_SERVICE_URI = 'http://localhost:8082/gateway-rest/admin';
 
    var factory = {
        fetchAllTemplate: fetchAllTemplate,
        createTemplate: createTemplate,
        updateTemplate: updateTemplate,
        deleteTemplate: deleteTemplate
    };
 
    return factory;
 
    function fetchAllTemplate() {
        var deferred = $q.defer();
        $http.get(REST_SERVICE_URI+"/messageTemplates")
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while fetching templates');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }
 
    function createTemplate(template) {
        var deferred = $q.defer();
        $http.post(REST_SERVICE_URI+"/createMessageTemplate", template)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while creating template');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }
 
 
    function updateTemplate(template, id) {
        var deferred = $q.defer();
        $http.put(REST_SERVICE_URI+id, template)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while updating template');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }
 
    function deleteTemplate(id) {
        var deferred = $q.defer();
        $http.delete(REST_SERVICE_URI+id)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while deleting template');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }
 
}]);