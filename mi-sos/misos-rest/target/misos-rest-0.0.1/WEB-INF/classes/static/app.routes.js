'use strict';

angular
    .module('app.routes', ['ngRoute'])
    .config(config);

function config ($routeProvider) {
    $routeProvider.
        when('/home', {
           /* templateUrl: 'sections/home/home.tpl.html',
            controller: 'HomeController'*/
            templateUrl: 'sections/dashboard/dashboard.tpl.html',
            controller: 'DashboardController'
        })
        .when('/', {
           /* templateUrl: 'sections/home/home.tpl.html',
            controller: 'HomeController'*/
        	 templateUrl: 'sections/dashboard/dashboard.tpl.html',
             controller: 'DashboardController'
        })
        /*.when('/users', {
            templateUrl: 'sections/users/users.tpl.html',
            controller: 'UsersController',
            css: 'sections/users/users.css'
        })*/
        .when('/dissemination', {
            templateUrl: 'sections/dissemination/dissemination.tpl.html',
            controller: 'DisseminationController'
        })
        .when('/statistics', {
            templateUrl: 'sections/statistics/statistics.tpl.html',
            controller: 'StatisticsController'
        })
        .when('/settings', {
            templateUrl: 'sections/settings/settings.tpl.html',
            controller: 'SettingsController'
        })
        .when('/agency', {
            templateUrl: 'sections/agency/agency.tpl.html',
            controller: 'AgencyController'
        })
        .when('/summary', {
            templateUrl: 'sections/summary/summary.tpl.html',
            controller: 'summaryController'
        })
        .when('/dashboard', {
            templateUrl: 'sections/dashboard/dashboard.tpl.html',
            controller: 'DashboardController'
        })
        .otherwise({
            redirectTo: '/'
        });
}