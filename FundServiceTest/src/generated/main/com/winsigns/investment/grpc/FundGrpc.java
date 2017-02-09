package com.winsigns.investment.grpc;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.0.3)",
    comments = "Source: fund.proto")
public class FundGrpc {

  private FundGrpc() {}

  public static final String SERVICE_NAME = "com.winsigns.investment.grpc.Fund";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.winsigns.investment.grpc.FundOuterClass.FundMessage,
      com.winsigns.investment.grpc.FundOuterClass.AddFundResponse> METHOD_ADD_FUND =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "com.winsigns.investment.grpc.Fund", "AddFund"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.winsigns.investment.grpc.FundOuterClass.FundMessage.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.winsigns.investment.grpc.FundOuterClass.AddFundResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.winsigns.investment.grpc.FundOuterClass.GetFundsRequest,
      com.winsigns.investment.grpc.FundOuterClass.GetFundsResponse> METHOD_GET_FUNDS =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "com.winsigns.investment.grpc.Fund", "GetFunds"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.winsigns.investment.grpc.FundOuterClass.GetFundsRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.winsigns.investment.grpc.FundOuterClass.GetFundsResponse.getDefaultInstance()));

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static FundStub newStub(io.grpc.Channel channel) {
    return new FundStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static FundBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new FundBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary and streaming output calls on the service
   */
  public static FundFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new FundFutureStub(channel);
  }

  /**
   */
  public static abstract class FundImplBase implements io.grpc.BindableService {

    /**
     */
    public void addFund(com.winsigns.investment.grpc.FundOuterClass.FundMessage request,
        io.grpc.stub.StreamObserver<com.winsigns.investment.grpc.FundOuterClass.AddFundResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_ADD_FUND, responseObserver);
    }

    /**
     */
    public void getFunds(com.winsigns.investment.grpc.FundOuterClass.GetFundsRequest request,
        io.grpc.stub.StreamObserver<com.winsigns.investment.grpc.FundOuterClass.GetFundsResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_FUNDS, responseObserver);
    }

    @java.lang.Override public io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_ADD_FUND,
            asyncUnaryCall(
              new MethodHandlers<
                com.winsigns.investment.grpc.FundOuterClass.FundMessage,
                com.winsigns.investment.grpc.FundOuterClass.AddFundResponse>(
                  this, METHODID_ADD_FUND)))
          .addMethod(
            METHOD_GET_FUNDS,
            asyncUnaryCall(
              new MethodHandlers<
                com.winsigns.investment.grpc.FundOuterClass.GetFundsRequest,
                com.winsigns.investment.grpc.FundOuterClass.GetFundsResponse>(
                  this, METHODID_GET_FUNDS)))
          .build();
    }
  }

  /**
   */
  public static final class FundStub extends io.grpc.stub.AbstractStub<FundStub> {
    private FundStub(io.grpc.Channel channel) {
      super(channel);
    }

    private FundStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FundStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new FundStub(channel, callOptions);
    }

    /**
     */
    public void addFund(com.winsigns.investment.grpc.FundOuterClass.FundMessage request,
        io.grpc.stub.StreamObserver<com.winsigns.investment.grpc.FundOuterClass.AddFundResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_ADD_FUND, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getFunds(com.winsigns.investment.grpc.FundOuterClass.GetFundsRequest request,
        io.grpc.stub.StreamObserver<com.winsigns.investment.grpc.FundOuterClass.GetFundsResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_GET_FUNDS, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class FundBlockingStub extends io.grpc.stub.AbstractStub<FundBlockingStub> {
    private FundBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private FundBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FundBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new FundBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.winsigns.investment.grpc.FundOuterClass.AddFundResponse addFund(com.winsigns.investment.grpc.FundOuterClass.FundMessage request) {
      return blockingUnaryCall(
          getChannel(), METHOD_ADD_FUND, getCallOptions(), request);
    }

    /**
     */
    public com.winsigns.investment.grpc.FundOuterClass.GetFundsResponse getFunds(com.winsigns.investment.grpc.FundOuterClass.GetFundsRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_GET_FUNDS, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class FundFutureStub extends io.grpc.stub.AbstractStub<FundFutureStub> {
    private FundFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private FundFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FundFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new FundFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.winsigns.investment.grpc.FundOuterClass.AddFundResponse> addFund(
        com.winsigns.investment.grpc.FundOuterClass.FundMessage request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_ADD_FUND, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.winsigns.investment.grpc.FundOuterClass.GetFundsResponse> getFunds(
        com.winsigns.investment.grpc.FundOuterClass.GetFundsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_GET_FUNDS, getCallOptions()), request);
    }
  }

  private static final int METHODID_ADD_FUND = 0;
  private static final int METHODID_GET_FUNDS = 1;

  private static class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final FundImplBase serviceImpl;
    private final int methodId;

    public MethodHandlers(FundImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_ADD_FUND:
          serviceImpl.addFund((com.winsigns.investment.grpc.FundOuterClass.FundMessage) request,
              (io.grpc.stub.StreamObserver<com.winsigns.investment.grpc.FundOuterClass.AddFundResponse>) responseObserver);
          break;
        case METHODID_GET_FUNDS:
          serviceImpl.getFunds((com.winsigns.investment.grpc.FundOuterClass.GetFundsRequest) request,
              (io.grpc.stub.StreamObserver<com.winsigns.investment.grpc.FundOuterClass.GetFundsResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    return new io.grpc.ServiceDescriptor(SERVICE_NAME,
        METHOD_ADD_FUND,
        METHOD_GET_FUNDS);
  }

}
