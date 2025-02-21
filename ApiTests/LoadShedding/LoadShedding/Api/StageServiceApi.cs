/*
 * Load Shedding API
 *
 * Endpoints for Load Shedding Api
 *
 * The version of the OpenAPI document: 1.0
 * Generated by: https://github.com/openapitools/openapi-generator.git
 */


using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Net;
using System.Net.Mime;
using LoadSheddingService.Swagger.Client;
using LoadSheddingService.Swagger.Model;

namespace LoadSheddingService.Swagger.Api
{

    /// <summary>
    /// Represents a collection of functions to interact with the API endpoints
    /// </summary>
    public interface IStageServiceApiSync : IApiAccessor
    {
        #region Synchronous Operations
        /// <summary>
        /// Get current load shedding stage
        /// </summary>
        /// <exception cref="LoadSheddingService.Swagger.Client.ApiException">Thrown when fails to make API call</exception>
        /// <param name="operationIndex">Index associated with the operation.</param>
        /// <returns>StageDO</returns>
        StageDO GetLoadSheddingStage(int operationIndex = 0);

        /// <summary>
        /// Get current load shedding stage
        /// </summary>
        /// <remarks>
        /// 
        /// </remarks>
        /// <exception cref="LoadSheddingService.Swagger.Client.ApiException">Thrown when fails to make API call</exception>
        /// <param name="operationIndex">Index associated with the operation.</param>
        /// <returns>ApiResponse of StageDO</returns>
        ApiResponse<StageDO> GetLoadSheddingStageWithHttpInfo(int operationIndex = 0);
        #endregion Synchronous Operations
    }

    /// <summary>
    /// Represents a collection of functions to interact with the API endpoints
    /// </summary>
    public interface IStageServiceApiAsync : IApiAccessor
    {
        #region Asynchronous Operations
        /// <summary>
        /// Get current load shedding stage
        /// </summary>
        /// <remarks>
        /// 
        /// </remarks>
        /// <exception cref="LoadSheddingService.Swagger.Client.ApiException">Thrown when fails to make API call</exception>
        /// <param name="operationIndex">Index associated with the operation.</param>
        /// <param name="cancellationToken">Cancellation Token to cancel the request.</param>
        /// <returns>Task of StageDO</returns>
        System.Threading.Tasks.Task<StageDO> GetLoadSheddingStageAsync(int operationIndex = 0, System.Threading.CancellationToken cancellationToken = default(System.Threading.CancellationToken));

        /// <summary>
        /// Get current load shedding stage
        /// </summary>
        /// <remarks>
        /// 
        /// </remarks>
        /// <exception cref="LoadSheddingService.Swagger.Client.ApiException">Thrown when fails to make API call</exception>
        /// <param name="operationIndex">Index associated with the operation.</param>
        /// <param name="cancellationToken">Cancellation Token to cancel the request.</param>
        /// <returns>Task of ApiResponse (StageDO)</returns>
        System.Threading.Tasks.Task<ApiResponse<StageDO>> GetLoadSheddingStageWithHttpInfoAsync(int operationIndex = 0, System.Threading.CancellationToken cancellationToken = default(System.Threading.CancellationToken));
        #endregion Asynchronous Operations
    }

    /// <summary>
    /// Represents a collection of functions to interact with the API endpoints
    /// </summary>
    public interface IStageServiceApi : IStageServiceApiSync, IStageServiceApiAsync
    {

    }

    /// <summary>
    /// Represents a collection of functions to interact with the API endpoints
    /// </summary>
    public partial class StageServiceApi : IStageServiceApi
    {
        private LoadSheddingService.Swagger.Client.ExceptionFactory _exceptionFactory = (name, response) => null;

        /// <summary>
        /// Initializes a new instance of the <see cref="StageServiceApi"/> class.
        /// </summary>
        /// <returns></returns>
        public StageServiceApi() : this((string)null)
        {
        }

        /// <summary>
        /// Initializes a new instance of the <see cref="StageServiceApi"/> class.
        /// </summary>
        /// <returns></returns>
        public StageServiceApi(string basePath)
        {
            this.Configuration = LoadSheddingService.Swagger.Client.Configuration.MergeConfigurations(
                LoadSheddingService.Swagger.Client.GlobalConfiguration.Instance,
                new LoadSheddingService.Swagger.Client.Configuration { BasePath = basePath }
            );
            this.Client = new LoadSheddingService.Swagger.Client.ApiClient(this.Configuration.BasePath);
            this.AsynchronousClient = new LoadSheddingService.Swagger.Client.ApiClient(this.Configuration.BasePath);
            this.ExceptionFactory = LoadSheddingService.Swagger.Client.Configuration.DefaultExceptionFactory;
        }

        /// <summary>
        /// Initializes a new instance of the <see cref="StageServiceApi"/> class
        /// using Configuration object
        /// </summary>
        /// <param name="configuration">An instance of Configuration</param>
        /// <returns></returns>
        public StageServiceApi(LoadSheddingService.Swagger.Client.Configuration configuration)
        {
            if (configuration == null) throw new ArgumentNullException("configuration");

            this.Configuration = LoadSheddingService.Swagger.Client.Configuration.MergeConfigurations(
                LoadSheddingService.Swagger.Client.GlobalConfiguration.Instance,
                configuration
            );
            this.Client = new LoadSheddingService.Swagger.Client.ApiClient(this.Configuration.BasePath);
            this.AsynchronousClient = new LoadSheddingService.Swagger.Client.ApiClient(this.Configuration.BasePath);
            ExceptionFactory = LoadSheddingService.Swagger.Client.Configuration.DefaultExceptionFactory;
        }

        /// <summary>
        /// Initializes a new instance of the <see cref="StageServiceApi"/> class
        /// using a Configuration object and client instance.
        /// </summary>
        /// <param name="client">The client interface for synchronous API access.</param>
        /// <param name="asyncClient">The client interface for asynchronous API access.</param>
        /// <param name="configuration">The configuration object.</param>
        public StageServiceApi(LoadSheddingService.Swagger.Client.ISynchronousClient client, LoadSheddingService.Swagger.Client.IAsynchronousClient asyncClient, LoadSheddingService.Swagger.Client.IReadableConfiguration configuration)
        {
            if (client == null) throw new ArgumentNullException("client");
            if (asyncClient == null) throw new ArgumentNullException("asyncClient");
            if (configuration == null) throw new ArgumentNullException("configuration");

            this.Client = client;
            this.AsynchronousClient = asyncClient;
            this.Configuration = configuration;
            this.ExceptionFactory = LoadSheddingService.Swagger.Client.Configuration.DefaultExceptionFactory;
        }

        /// <summary>
        /// The client for accessing this underlying API asynchronously.
        /// </summary>
        public LoadSheddingService.Swagger.Client.IAsynchronousClient AsynchronousClient { get; set; }

        /// <summary>
        /// The client for accessing this underlying API synchronously.
        /// </summary>
        public LoadSheddingService.Swagger.Client.ISynchronousClient Client { get; set; }

        /// <summary>
        /// Gets the base path of the API client.
        /// </summary>
        /// <value>The base path</value>
        public string GetBasePath()
        {
            return this.Configuration.BasePath;
        }

        /// <summary>
        /// Gets or sets the configuration object
        /// </summary>
        /// <value>An instance of the Configuration</value>
        public LoadSheddingService.Swagger.Client.IReadableConfiguration Configuration { get; set; }

        /// <summary>
        /// Provides a factory method hook for the creation of exceptions.
        /// </summary>
        public LoadSheddingService.Swagger.Client.ExceptionFactory ExceptionFactory
        {
            get
            {
                if (_exceptionFactory != null && _exceptionFactory.GetInvocationList().Length > 1)
                {
                    throw new InvalidOperationException("Multicast delegate for ExceptionFactory is unsupported.");
                }
                return _exceptionFactory;
            }
            set { _exceptionFactory = value; }
        }

        /// <summary>
        /// Get current load shedding stage 
        /// </summary>
        /// <exception cref="LoadSheddingService.Swagger.Client.ApiException">Thrown when fails to make API call</exception>
        /// <param name="operationIndex">Index associated with the operation.</param>
        /// <returns>StageDO</returns>
        public StageDO GetLoadSheddingStage(int operationIndex = 0)
        {
            LoadSheddingService.Swagger.Client.ApiResponse<StageDO> localVarResponse = GetLoadSheddingStageWithHttpInfo();
            return localVarResponse.Data;
        }

        /// <summary>
        /// Get current load shedding stage 
        /// </summary>
        /// <exception cref="LoadSheddingService.Swagger.Client.ApiException">Thrown when fails to make API call</exception>
        /// <param name="operationIndex">Index associated with the operation.</param>
        /// <returns>ApiResponse of StageDO</returns>
        public LoadSheddingService.Swagger.Client.ApiResponse<StageDO> GetLoadSheddingStageWithHttpInfo(int operationIndex = 0)
        {
            LoadSheddingService.Swagger.Client.RequestOptions localVarRequestOptions = new LoadSheddingService.Swagger.Client.RequestOptions();

            string[] _contentTypes = new string[] {
            };

            // to determine the Accept header
            string[] _accepts = new string[] {
                "application/json",
                "text/plain"
            };

            var localVarContentType = LoadSheddingService.Swagger.Client.ClientUtils.SelectHeaderContentType(_contentTypes);
            if (localVarContentType != null)
            {
                localVarRequestOptions.HeaderParameters.Add("Content-Type", localVarContentType);
            }

            var localVarAccept = LoadSheddingService.Swagger.Client.ClientUtils.SelectHeaderAccept(_accepts);
            if (localVarAccept != null)
            {
                localVarRequestOptions.HeaderParameters.Add("Accept", localVarAccept);
            }


            localVarRequestOptions.Operation = "StageServiceApi.GetLoadSheddingStage";
            localVarRequestOptions.OperationIndex = operationIndex;


            // make the HTTP request
            var localVarResponse = this.Client.Get<StageDO>("/stage", localVarRequestOptions, this.Configuration);
            if (this.ExceptionFactory != null)
            {
                Exception _exception = this.ExceptionFactory("GetLoadSheddingStage", localVarResponse);
                if (_exception != null)
                {
                    throw _exception;
                }
            }

            return localVarResponse;
        }

        /// <summary>
        /// Get current load shedding stage 
        /// </summary>
        /// <exception cref="LoadSheddingService.Swagger.Client.ApiException">Thrown when fails to make API call</exception>
        /// <param name="operationIndex">Index associated with the operation.</param>
        /// <param name="cancellationToken">Cancellation Token to cancel the request.</param>
        /// <returns>Task of StageDO</returns>
        public async System.Threading.Tasks.Task<StageDO> GetLoadSheddingStageAsync(int operationIndex = 0, System.Threading.CancellationToken cancellationToken = default(System.Threading.CancellationToken))
        {
            LoadSheddingService.Swagger.Client.ApiResponse<StageDO> localVarResponse = await GetLoadSheddingStageWithHttpInfoAsync(operationIndex, cancellationToken).ConfigureAwait(false);
            return localVarResponse.Data;
        }

        /// <summary>
        /// Get current load shedding stage 
        /// </summary>
        /// <exception cref="LoadSheddingService.Swagger.Client.ApiException">Thrown when fails to make API call</exception>
        /// <param name="operationIndex">Index associated with the operation.</param>
        /// <param name="cancellationToken">Cancellation Token to cancel the request.</param>
        /// <returns>Task of ApiResponse (StageDO)</returns>
        public async System.Threading.Tasks.Task<LoadSheddingService.Swagger.Client.ApiResponse<StageDO>> GetLoadSheddingStageWithHttpInfoAsync(int operationIndex = 0, System.Threading.CancellationToken cancellationToken = default(System.Threading.CancellationToken))
        {

            LoadSheddingService.Swagger.Client.RequestOptions localVarRequestOptions = new LoadSheddingService.Swagger.Client.RequestOptions();

            string[] _contentTypes = new string[] {
            };

            // to determine the Accept header
            string[] _accepts = new string[] {
                "application/json",
                "text/plain"
            };

            var localVarContentType = LoadSheddingService.Swagger.Client.ClientUtils.SelectHeaderContentType(_contentTypes);
            if (localVarContentType != null)
            {
                localVarRequestOptions.HeaderParameters.Add("Content-Type", localVarContentType);
            }

            var localVarAccept = LoadSheddingService.Swagger.Client.ClientUtils.SelectHeaderAccept(_accepts);
            if (localVarAccept != null)
            {
                localVarRequestOptions.HeaderParameters.Add("Accept", localVarAccept);
            }


            localVarRequestOptions.Operation = "StageServiceApi.GetLoadSheddingStage";
            localVarRequestOptions.OperationIndex = operationIndex;


            // make the HTTP request
            var localVarResponse = await this.AsynchronousClient.GetAsync<StageDO>("/stage", localVarRequestOptions, this.Configuration, cancellationToken).ConfigureAwait(false);

            if (this.ExceptionFactory != null)
            {
                Exception _exception = this.ExceptionFactory("GetLoadSheddingStage", localVarResponse);
                if (_exception != null)
                {
                    throw _exception;
                }
            }

            return localVarResponse;
        }

    }
}
