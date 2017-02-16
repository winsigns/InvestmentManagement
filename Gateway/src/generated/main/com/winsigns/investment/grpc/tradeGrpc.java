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
    comments = "Source: TradeService.proto")
public class tradeGrpc {

  private tradeGrpc() {}

  public static final String SERVICE_NAME = "com.winsigns.investment.grpc.trade";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.winsigns.investment.grpc.InstructionOuterClass.Instruction,
      com.winsigns.investment.grpc.InstructionOuterClass.Instruction> METHOD_VIRTUAL_DONE =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "com.winsigns.investment.grpc.trade", "virtualDone"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.winsigns.investment.grpc.InstructionOuterClass.Instruction.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.winsigns.investment.grpc.InstructionOuterClass.Instruction.getDefaultInstance()));

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static tradeStub newStub(io.grpc.Channel channel) {
    return new tradeStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static tradeBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new tradeBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary and streaming output calls on the service
   */
  public static tradeFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new tradeFutureStub(channel);
  }

  /**
   */
  public static abstract class tradeImplBase implements io.grpc.BindableService {

    /**
     */
    public void virtualDone(com.winsigns.investment.grpc.InstructionOuterClass.Instruction request,
        io.grpc.stub.StreamObserver<com.winsigns.investment.grpc.InstructionOuterClass.Instruction> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_VIRTUAL_DONE, responseObserver);
    }

    @java.lang.Override public io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_VIRTUAL_DONE,
            asyncUnaryCall(
              new MethodHandlers<
                com.winsigns.investment.grpc.InstructionOuterClass.Instruction,
                com.winsigns.investment.grpc.InstructionOuterClass.Instruction>(
                  this, METHODID_VIRTUAL_DONE)))
          .build();
    }
  }

  /**
   */
  public static final class tradeStub extends io.grpc.stub.AbstractStub<tradeStub> {
    private tradeStub(io.grpc.Channel channel) {
      super(channel);
    }

    private tradeStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected tradeStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new tradeStub(channel, callOptions);
    }

    /**
     */
    public void virtualDone(com.winsigns.investment.grpc.InstructionOuterClass.Instruction request,
        io.grpc.stub.StreamObserver<com.winsigns.investment.grpc.InstructionOuterClass.Instruction> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_VIRTUAL_DONE, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class tradeBlockingStub extends io.grpc.stub.AbstractStub<tradeBlockingStub> {
    private tradeBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private tradeBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected tradeBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new tradeBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.winsigns.investment.grpc.InstructionOuterClass.Instruction virtualDone(com.winsigns.investment.grpc.InstructionOuterClass.Instruction request) {
      return blockingUnaryCall(
          getChannel(), METHOD_VIRTUAL_DONE, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class tradeFutureStub extends io.grpc.stub.AbstractStub<tradeFutureStub> {
    private tradeFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private tradeFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected tradeFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new tradeFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.winsigns.investment.grpc.InstructionOuterClass.Instruction> virtualDone(
        com.winsigns.investment.grpc.InstructionOuterClass.Instruction request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_VIRTUAL_DONE, getCallOptions()), request);
    }
  }

  private static final int METHODID_VIRTUAL_DONE = 0;

  private static class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final tradeImplBase serviceImpl;
    private final int methodId;

    public MethodHandlers(tradeImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_VIRTUAL_DONE:
          serviceImpl.virtualDone((com.winsigns.investment.grpc.InstructionOuterClass.Instruction) request,
              (io.grpc.stub.StreamObserver<com.winsigns.investment.grpc.InstructionOuterClass.Instruction>) responseObserver);
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
        METHOD_VIRTUAL_DONE);
  }

}
