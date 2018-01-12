'use strict';
/*angular
    .module('app.core')
    .controller('DisseminationController', function($scope, PageValues) {

    });*/

  angular
    .module('app.core',['angularUtils.directives.dirPagination'])
    .controller('DisseminationController', function($scope, $http) {


       //LIST OF MESSAGES  
       $http.get('http://10.1.70.84:8080/mobilegateway/admin/displayIocMessages').
       //$http.get('http://localhost:8082/gateway-rest/admin/displayIocMessages').
        then(function(response){
            //data from web service
            $scope.messages= response.data._embedded.iocMessagesViewList;
            //sorting 
            $scope.sort = function(keyname){
                $scope.sortKey = keyname;   //set the sortKey to the param passed
                $scope.reverse = !$scope.reverse; //if true make it false and vice versa
            }; 
        });


        //LIST OF MESSAGE TEMPLATES
        $http.get('http://10.1.70.84:8080/mobilegateway/admin/messageTemplates').
         then(function(listTemplatesResponse){
            //data from web service
            $scope.templates= listTemplatesResponse.data._embedded.messageTemplateList;
            //sorting 
            $scope.sort = function(keyname){
                $scope.sortKey = keyname;   //set the sortKey to the param passed
                $scope.reverse = !$scope.reverse; //if true make it false and vice versa
            };  
          });

         //ADD MESSAGE TEMPLATE
         $scope.submit = function(templateId,templateName){
          alert("submitted"+templateId+templateName);
          var addtemplate={messageTemplateId:templateId,messageTemplate:templateName};
          $http.post('http://localhost:8082/gateway-rest/admin/createMessageTemplate', addtemplate)
            .then(
            function (addTemplateResponse) {
               alert("hi"); 
               
            },
            function(errResponse){
                console.error('Error while creating template');
            }
          );

          }

     });

   

   
     

