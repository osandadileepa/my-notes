(function () {

    let app = angular.module('notesApp', ['ngRoute', 'ngMaterial']);
    const USER_KEY = 'user';

    app.config(['$locationProvider', '$routeProvider', function ($locationProvider, $routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: '/partials/notes-view.html',
                controller: 'notesController'
            })
            .when('/login', {
                templateUrl: '/partials/login.html',
                controller: 'loginController',
            })
            .otherwise('/');
    }
    ]);

    app.run(['$rootScope', '$location', 'AuthService', '$window', function ($rootScope, $location, AuthService, $window) {

        $rootScope.$on('$routeChangeStart', function (event) {

            if ($location.path() === "/login") {
                return;
            }

            let user = $window.localStorage.getItem(USER_KEY);

            if (user) {
                const userObj = JSON.parse(user)
                if (!userObj.success) {
                    console.log('Access Denied, user not logged in!!');
                    event.preventDefault();
                    $location.path('/login');
                }
            } else {
                console.log('Access Denied, user info not available!!');
                event.preventDefault();
                $location.path('/login');
            }
        });

        $rootScope.logout = function () {
            $window.localStorage.removeItem(USER_KEY);
            console.log('User Logout successful !!!!!');
            $location.path('/login');
        }
    }]);


    app.service('AuthService', function ($http, $rootScope, $window) {
        function userLogin(username, password) {
            return $http.post("api/login", {username: username, password: password}).then(function (user) {
                if (user.status === 200) {
                    $window.localStorage.setItem(USER_KEY, JSON.stringify(user.data));
                }
            });
        }

        function getUser() {
            let user = $window.localStorage.getItem(USER_KEY);
            if (user) {
                return JSON.parse(user);
            }
            return null;
        }

        return {
            userLogin: userLogin,
            getUser: getUser
        }
    });

    app.service('NoteService', function ($http) {
        function getNotes(username) {
            return $http.get("api/get-notes?username=" + username);
        }

        function saveOrUpdateNote(username, note) {
            return $http.post("api/save?username=" + username, note);
        }

        return {
            getNotes: getNotes,
            saveOrUpdateNote, saveOrUpdateNote
        }
    });

    app.controller('loginController', function ($scope, AuthService, $location, $rootScope) {
        $rootScope.loginSuccess = false;
        $scope.invalidCreds = false;
        $scope.login = {
            username: null,
            password: null
        };

        $scope.login = function () {
            AuthService.userLogin($scope.login.username, $scope.login.password).then(function (user) {
                $location.path("/");
            }, function (error) {
                console.log('Error login with username and password', error);
                $scope.invalidCreds = true;
            });
        };
    });


    app.controller('notesController', function ($scope, AuthService, NoteService) {
        $scope.isEditCreateView = false;

        $scope.getNotesForUser = function () {
            $scope.userDetails = AuthService.getUser();
            if ($scope.userDetails) {
                let username = $scope.userDetails.user.username;
                NoteService.getNotes(username).then(function (response) {
                    $scope.userNotes = response.data;
                }, function (error) {
                    console.log('Error getting User Notes', error);
                });
            }
        };
        $scope.getNotesForUser();

        $scope.newNoteView = function () {
            $scope.isEditCreateView = true;
            $scope.note = {
                name: undefined,
                summary: undefined,
                id: undefined
            };
        };

        $scope.cancel = function () {
            $scope.isEditCreateView = false;
        };

        $scope.viewNote = function (note) {
            $scope.isEditCreateView = true;
            $scope.note = {
                name: note.name,
                summary: note.summary,
                id: note.id
            };
        }

        $scope.saveOrUpdate = function (note) {
            let username = $scope.userDetails.user.username;
            NoteService.saveOrUpdateNote(username, note).then(function (response) {
                console.log("Update or Save noted", response);
                $scope.getNotesForUser();
            }, function (error) {
                console.log('Error saving or updating notes', error);
            });
        };
    });
})();