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
    comments = "Source: CaptialService.proto")
public class capitalGrpc {

  private capitalGrpc() {}

  public static final String SERVICE_NAME = "com.winsigns.investment.grpc.capital";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.winsigns.investment.grpc.CaptialService.OneCapitalMsg,
      com.winsigns.investment.grpc.CaptialService.MutilCapitalMsg> METHOD_QUERY_CAPITAL =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "com.winsigns.investment.grpc.capital", "queryCapital"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.winsigns.investment.grpc.CaptialService.OneCapitalMsg.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.winsigns.investment.grpc.CaptialService.MutilCapitalMsg.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.winsigns.investment.grpc.CaptialService.CapitalOperatorMsg,
      com.winsigns.investment.grpc.CaptialService.CapitalOperatorMsg> METHOD_OPERATOR_CAPITAL =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "com.winsigns.investment.grpc.capital", "operatorCapital"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.winsigns.investment.grpc.CaptialService.CapitalOperatorMsg.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.winsigns.investment.grpc.CaptialService.CapitalOperatorMsg.getDefaultInstance()));

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static capitalStub newStub(io.grpc.Channel channel) {
    return new capitalStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static capitalBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new capitalBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary and streaming output calls on the service
   */
  public static capitalFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new capitalFutureStub(channel);
  }

  /**
   */
  public static abstract class capitalImplBase implements io.grpc.BindableService {

    /**
     */
    public void queryCapital(com.winsigns.investment.grpc.CaptialService.OneCapitalMsg request,
        io.grpc.stub.StreamObserver<com.winsigns.investment.grpc.CaptialService.MutilCapitalMsg> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_QUERY_CAPITAL, responseObserver);
    }

    /**
     */
    public void operatorCapital(com.winsigns.investment.grpc.CaptialService.CapitalOperatorMsg request,
        io.grpc.stub.StreamObserver<com.winsigns.investment.grpc.CaptialService.CapitalOperatorMsg> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_OPERATOR_CAPITAL, responseObserver);
    }

    @java.lang.Override public io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_QUERY_CAPITAL,
            asyncUnaryCall(
              new MethodHandlers<
                com.winsigns.investment.grpc.CaptialService.OneCapitalMsg,
                com.winsigns.investment.grpc.CaptialService.MutilCapitalMsg>(
                  this, METHODID_QUERY_CAPITAL)))
          .addMethod(
            METHOD_OPERATOR_CAPITAL,
            asyncUnaryCall(
              new MethodHandlers<
                com.winsigns.investment.grpc.CaptialService.CapitalOperatorMsg,
                com.winsigns.investment.grpc.CaptialService.CapitalOperatorMsg>(
                  this, METHODID_OPERATOR_CAPITAL)))
          .build();
    }
  }

  /**
   */
  public static final class capitalStub extends io.grpc.stub.AbstractStub<capitalStub> {
    private capitalStub(io.grpc.Channel channel) {
      super(channel);
    }

    private capitalStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected capitalStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new capitalStub(channel, callOptions);
    }

    /**
     */
    public void queryCapital(com.winsigns.investment.grpc.CaptialService.OneCapitalMsg request,
        io.grpc.stub.StreamObserver<com.winsigns.investment.grpc.CaptialService.MutilCapitalMsg> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_QUERY_CAPITAL, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void operatorCapital(com.winsigns.investment.grpc.CaptialService.CapitalOperatorMsg request,
        io.grpc.stub.StreamObserver<com.winsigns.investment.grpc.CaptialService.CapitalOperatorMsg> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_OPERATOR_CAPITAL, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class capitalBlockingStub extends io.grpc.stub.AbstractStub<capitalBlockingStub> {
    private capitalBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private capitalBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected capitalBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new capitalBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.winsigns.investment.grpc.CaptialService.MutilCapitalMsg queryCapital(com.winsigns.investment.grpc.CaptialService.OneCapitalMsg request) {
      return blockingUnaryCall(
          getChannel(), METHOD_QUERY_CAPITAL, getCallOptions(), request);
    }

    /**
     */
    public com.winsigns.investment.grpc.CaptialService.CapitalOperatorMsg operatorCapital(com.winsigns.investment.grpc.CaptialService.CapitalOperatorMsg request) {
      return blockingUnaryCall(
          getChannel(), METHOD_OPERATOR_CAPITAL, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class capitalFutureStub extends io.grpc.stub.AbstractStub<capitalFutureStub> {
    private capitalFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private capitalFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected capitalFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new capitalFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.winsigns.investment.grpc.CaptialService.MutilCapitalMsg> queryCapital(
        com.winsigns.investment.grpc.CaptialService.OneCapitalMsg request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_QUERY_CAPITAL, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.winsigns.investment.grpc.CaptialService.CapitalOperatorMsg> operatorCapital(
        com.winsigns.investment.grpc.CaptialService.CapitalOperatorMsg request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_OPERATOR_CAPITAL, getCallOptions()), request);
    }
  }

  private static final int METHODID_QUERY_CAPITAL = 0;
  private static final int METHODID_OPERATOR_CAPITAL = 1;

  private static class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final capitalImplBase serviceImpl;
    private final int methodId;

    public MethodHandlers(capitalImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_QUERY_CAPITAL:
          serviceImpl.queryCapital((com.winsigns.investment.grpc.CaptialService.OneCapitalMsg) request,
              (io.grpc.stub.StreamObserver<com.winsigns.investment.grpc.CaptialService.MutilCapitalMsg>) responseObserver);
          break;
        case METHODID_OPERATOR_CAPITAL:
          serviceImpl.operatorCapital((com.winsigns.investment.grpc.CaptialService.CapitalOperatorMsg) request,
              (io.grpc.stub.StreamObserver<com.winsigns.investment.grpc.CaptialService.CapitalOperatorMsg>) responseObserver);
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
        METHOD_QUERY_CAPITAL,
        METHOD_OPERATOR_CAPITAL);
  }

}
