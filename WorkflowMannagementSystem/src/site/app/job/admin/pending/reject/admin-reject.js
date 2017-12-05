(function () {
    angular.module("AppModule")
            .controller("AdminRejectController", function ($scope, $rootScope, Notification, Factory) {
                $scope.model = {};
                $scope.ui = {};
                $scope.model.job = {};
                $scope.model.newJobList = [];
                $scope.model.budgetCodeList = [];
                $scope.listIndex = 0;
                $scope.ui.mode = true;

                var findAllUrl = "/api/wms/job/get-all-jobs-by-department-and-reject/" + 1;
                var findAllCodeUrl = "/api/wms/master/budget-code/find-all-budget-code";
                var saveUrl = "/api/wms/job/save-jobs";
                var findAllTransactionUrl = "/api/wms/job-transaction/get-all-job-transaction/";

                $scope.ui.reset = function () {
                    $scope.model.job = {};
                    $scope.ui.selectedJobIndex = null;
                };

                $scope.ui.save = function () {
                    if ($scope.ui.mode) {
                        $scope.ui.mode = false;
                        var detail = $scope.model.job;
                        detail.status = "UNAPPROVE";
                        var detailJSON = JSON.stringify(detail);
                        console.log(detailJSON);
                        Factory.save(saveUrl, detailJSON,
                                function (data) {
                                    Notification.success(data.indexNo + " - " + "Job Send To Approval");
                                    $scope.model.newJobList.splice($scope.listIndex, 1);
                                    $scope.ui.reset();
                                    $rootScope.model.map.REJECT -= 1;
                                    $rootScope.model.map.UNAPPROVE += 1;
                                    $scope.ui.mode = true;
                                },
                                function (data) {
                                    Notification.error(data.message);
                                    $scope.ui.mode = true;
                                }
                        );
                    }
                };

                $scope.ui.setDescription = function (job, index) {
                    $scope.ui.mode = 'select';
                    $scope.listIndex = index;
                    $scope.ui.selectedJobIndex = job.indexNo;
                    $scope.model.job = job;
                    $scope.ui.jobTransactions(job.indexNo);
//                    $scope.model.job.description = job.clientDescription;
                };

                $scope.ui.budgetCodeLabel = function (indexNo) {
                    var budgetCode;
                    angular.forEach($scope.model.budgetCodeList, function (value) {
                        if (value.indexNo === parseInt(indexNo)) {
                            budgetCode = value.code;
                            return;
                        }
                    });
                    return budgetCode;
                };

                $scope.ui.jobTransactions = function (indexNo) {
                    Factory.findAll(findAllTransactionUrl + indexNo, function (data) {
                        $scope.model.transactionList = data;
                    });
                };

                $scope.ui.init = function () {
                    Factory.getCountList("/api/wms/count/get-all-count", function (data) {
                        $rootScope.model.map = data;
                    });
                    $scope.ui.mode = 'unselect';
                    Factory.findAll(findAllUrl, function (data) {
                        $scope.model.newJobList = data;
                    });
                    Factory.findAll(findAllCodeUrl, function (data) {
                        $scope.model.budgetCodeList = data;
                    });
                };
                $scope.ui.init();
            });
}());