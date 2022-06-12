/* Dynamic Programming Java implementation
of LIS problem */

package dp;

class LISDP {
    /* lis() returns the length of the longest
    increasing subsequence in arr[] of size n */
    static int lis(int arr[], int n)
    {
        int lis[] = new int[n];
        int i, j, max = 0;

        /* Initialize LIS values for all indexes */
        for (i = 0; i < n; i++)
            lis[i] = 1;

		/* Compute optimized LIS values in
		bottom up manner */
        for (i = 1; i < n; i++)
            for (j = 0; j < i; j++)
                if (arr[i] > arr[j] && lis[i] < lis[j] + 1)
                    lis[i] = lis[j] + 1;

        /* Pick maximum of all LIS values */
        for (i = 0; i < n; i++) {
            System.out.println("value: " + lis[i]);
            if (max < lis[i])
                max = lis[i];
        }

        return max;
    }

    public static void main(String args[])
    {
        int arr[] = { 10, 22, 9, 33, 21, 50, 41, 60 };
        int n = arr.length;
        System.out.println("Length of lis is " + lis(arr, n)
                + "\n");
        System.out.println("CLEANUP_FAILED_WORKFLOW: NonRetryableException state=UpdateGgsaDeploymentWorkflow.State(currentDeploymentData=DeploymentData(id=ocid1.goldengatedeployment.oc1.iad.amaaaaaarhwbfeqamnddmpgxt5lozhlh5wwl42sihwtobent6wwym7z5jysa, timeCreated=null, timeUpdated=null, state=Updating, subState=None, lifecycleDetails=LifecycleDetails(lifecycleDetailsType=null, time=0, opcRequestId=null, messageKey=null, messageArguments=null), stateMessage=null, endpointServiceId=ocid1.endpointservice.oc1.iad.aaaaaaaaggr2vzzrtg5syiyi5mymk7wafu64ev66zvqux4meqvnhnvgiywpa, privateEndpointId=ocid1.privateendpoint.oc1.iad.aaaaaaaajkluhhcyykl7itzoukmfe7eujt4djhrwzbsgkxmbx2zel4nynhlq, pePrivateIp=10.0.0.71, instanceId=null, bootVolumeId=null, vnicId=null, vnicIp=null, mountTargetId=ocid1.mounttarget.oc1.iad.aaaaaby27vfqonqsnfqwillqojxwiotjmfsc2ylefuyqaaaa, mountTargetIp=null, fileSystemId=ocid1.filesystem.oc1.iad.aaaaaaaaaablrcjwnfqwillqojxwiotjmfsc2ylefuyqaaaa, exportId=ocid1.export.oc1.iad.aaaaaa4np2tjxufqnfqwillqojxwiotjmfsc2ylefuyqaaaa, loadBalancerId=ocid1.loadbalancer.oc1.iad.aaaaaaaanmjwsjssayjhyj65lgr7im4bhvvwta7vzcdpt3uvyaiuhuziwqaq, publicIpAddress=130.35.231.188, instanceCreationId=null, lastAcceptedRequestToken=1559384, lastCompletedRequestToken=1559384, version=1076895, timeUpgradeRequired=null, notificationSent=0, storageUtilizationInBytes=null, isStorageUtilizationLimitExceeded=null, upgradeHistory=[], streamPoolId=ocid1.streampool.oc1.iad.amaaaaaacifmr2aawryi3zqgigl7myuwrz76u6l2i47eibycsmoevlacksrq, clusterId=ocid1.cluster.oc1.iad.aaaaaaaaxx5kglzl3br2hr6p6372uh5pcuppsn2rjqg3xf7dzcdwuo6a4lbq, deploymentNamespace=lh5wwl42sihwtobent6wwym7z5jysa, fileSystemMeteredBytes=null), privateEndpointId=null, privateEndpointWorkRequestId=null, privateEndpointPrivateIP=null, launchedSubWorkflowDetails={}):\ncom.oracle.pic.workflow.worker.exceptions.NonRetryableException: UpdateGgsaDeploymentWorkflow FAILED.\n\tat com.oracle.ggs.worker.workflows.core.EnhancedWorkflow.createNonRetryableException(EnhancedWorkflow.java:123)\n\tat com.oracle.ggs.worker.workflows.core.EnhancedWorkflow.nonRetryableException(EnhancedWorkflow.java:102)\n\tat com.oracle.ggs.worker.workflows.core.EnhancedWorkflow.nonRetryableException(EnhancedWorkflow.java:98)\n\tat com.oracle.ggs.worker.workflows.core.EnhancedWorkflow.terminateAsFailure(EnhancedWorkflow.java:135)\n\tat com.oracle.ggs.worker.workflows.ggsa.UpdateGgsaDeploymentWorkflow.cleanupFailedWorkflow(UpdateGgsaDeploymentWorkflow.java:612)\n\tat com.oracle.ggs.worker.workflows.interceptor.AbstractExitOnTerminalStateInterceptor.apply(AbstractExitOnTerminalStateInterceptor.java:100)\n\tat com.oracle.pic.workflow.worker.util.InterceptorShim.apply(InterceptorShim.java:18)\n\tat com.oracle.ggs.faults.core.FaultInjectionWorkflowInterceptor.apply(FaultInjectionWorkflowInterceptor.java:47)\n\tat com.oracle.pic.workflow.worker.util.InterceptorShim.apply(InterceptorShim.java:18)\n\tat com.oracle.ggs.worker.workflows.core.WorkflowEnhancementInterceptor.apply(WorkflowEnhancementInterceptor.java:33)\n\tat com.oracle.pic.workflow.worker.util.InterceptorShim.apply(InterceptorShim.java:18)\n\tat com.oracle.ggs.worker.workflows.core.BackoffStrategyInterceptor.apply(BackoffStrategyInterceptor.java:93)\n\tat com.oracle.pic.workflow.worker.util.InterceptorShim.apply(InterceptorShim.java:18)\n\tat com.oracle.ggs.worker.workflows.core.PollStepInterceptor.apply(PollStepInterceptor.java:29)\n\tat com.oracle.pic.workflow.worker.util.InterceptorShim.apply(InterceptorShim.java:18)\n\tat com.oracle.ggs.worker.workflows.core.WorkflowStateProviderInterceptor.apply(WorkflowStateProviderInterceptor.java:47)\n\tat com.oracle.pic.workflow.worker.util.InterceptorShim.apply(InterceptorShim.java:18)\n\tat com.oracle.ggs.worker.workflows.core.WorkRequestHandlerInterceptor.apply(WorkRequestHandlerInterceptor.java:50)\n\tat com.oracle.pic.workflow.worker.util.InterceptorShim.apply(InterceptorShim.java:18)\n\tat com.oracle.ggs.worker.workflows.interceptor.OpcRequestInterceptor.apply(OpcRequestInterceptor.java:34)\n\tat com.oracle.ggs.worker.workflows.interceptor.OpcRequestInterceptor.apply(OpcRequestInterceptor.java:22)\n\tat com.oracle.pic.workflow.worker.util.InterceptorShim.apply(InterceptorShim.java:18)\n\tat com.oracle.ggs.worker.workflows.core.LogContextInterceptor.apply(LogContextInterceptor.java:35)\n\tat com.oracle.pic.workflow.worker.util.InterceptorShim.apply(InterceptorShim.java:18)\n\tat com.oracle.pic.workflow.worker.core.WorkerTask.apply(WorkerTask.java:109)\n\tat com.oracle.pic.workflow.worker.core.WorkDoingThread.doTheWork(WorkDoingThread.java:159)\n\tat com.oracle.pic.workflow.worker.core.WorkDoingThread.runOneIterationAndCatchExpectedInterrupts(WorkDoingThread.java:65)\n\tat com.oracle.pic.workflow.worker.core.InterruptableExecutionThreadService.run(InterruptableExecutionThreadService.java:50)\n\tat com.google.common.util.concurrent.AbstractExecutionThreadService$1$2.run(AbstractExecutionThreadService.java:67)\n\tat com.google.common.util.concurrent.Callables$4.run(Callables.java:121)\n\tat java.lang.Thread.run(Thread.java:748)\n");
    }
}
/*This code is contributed by Rajat Mishra*/
