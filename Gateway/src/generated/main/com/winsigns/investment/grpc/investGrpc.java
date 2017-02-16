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
    comments = "Source: InvestService.proto")
public class investGrpc {

  private investGrpc() {}

  public static final String SERVICE_NAME = "com.winsigns.investment.grpc.invest";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.winsigns.investment.grpc.InstructionOuterClass.Instruction,
      com.winsigns.investment.grpc.InstructionOuterClass.Instruction> METHOD_CREATE_INSTRUCTION =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "com.winsigns.investment.grpc.invest", "createInstruction"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.winsigns.investment.grpc.InstructionOuterClass.Instruction.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.winsigns.investment.grpc.InstructionOuterClass.Instruction.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.winsigns.investment.grpc.InstructionCommitOuterClass.InstructionCommit,
      com.winsigns.investment.grpc.InstructionCommitOuterClass.InstructionCommit> METHOD_COMMIT_INSTRUCTION =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "com.winsigns.investment.grpc.invest", "commitInstruction"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.winsigns.investment.grpc.InstructionCommitOuterClass.InstructionCommit.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.winsigns.investment.grpc.InstructionCommitOuterClass.InstructionCommit.getDefaultInstance()));

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static investStub newStub(io.grpc.Channel channel) {
    return new investStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static investBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new investBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary and streaming output calls on the service
   */
  public static investFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new investFutureStub(channel);
  }

  /**
   */
  public static abstract class investImplBase implements io.grpc.BindableService {

    /**
     */
    public void createInstruction(com.winsigns.investment.grpc.InstructionOuterClass.Instruction request,
        io.grpc.stub.StreamObserver<com.winsigns.investment.grpc.InstructionOuterClass.Instruction> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_CREATE_INSTRUCTION, responseObserver);
    }

    /**
     */
    public void commitInstruction(com.winsigns.investment.grpc.InstructionCommitOuterClass.InstructionCommit request,
        io.grpc.stub.StreamObserver<com.winsigns.investment.grpc.InstructionCommitOuterClass.InstructionCommit> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_COMMIT_INSTRUCTION, responseObserver);
    }

    @java.lang.Override public io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_CREATE_INSTRUCTION,
            asyncUnaryCall(
              new MethodHandlers<
                com.winsigns.investment.grpc.InstructionOuterClass.Instruction,
                com.winsigns.investment.grpc.InstructionOuterClass.Instruction>(
                  this, METHODID_CREATE_INSTRUCTION)))
          .addMethod(
            METHOD_COMMIT_INSTRUCTION,
            asyncUnaryCall(
              new MethodHandlers<
                com.winsigns.investment.grpc.InstructionCommitOuterClass.InstructionCommit,
                com.winsigns.investment.grpc.InstructionCommitOuterClass.InstructionCommit>(
                  this, METHODID_COMMIT_INSTRUCTION)))
          .build();
    }
  }

  /**
   */
  public static final class investStub extends io.grpc.stub.AbstractStub<investStub> {
    private investStub(io.grpc.Channel channel) {
      super(channel);
    }

    private investStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected investStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new investStub(channel, callOptions);
    }

    /**
     */
    public void createInstruction(com.winsigns.investment.grpc.InstructionOuterClass.Instruction request,
        io.grpc.stub.StreamObserver<com.winsigns.investment.grpc.InstructionOuterClass.Instruction> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_CREATE_INSTRUCTION, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void commitInstruction(com.winsigns.investment.grpc.InstructionCommitOuterClass.InstructionCommit request,
        io.grpc.stub.StreamObserver<com.winsigns.investment.grpc.InstructionCommitOuterClass.InstructionCommit> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_COMMIT_INSTRUCTION, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class investBlockingStub extends io.grpc.stub.AbstractStub<investBlockingStub> {
    private investBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private investBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected investBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new investBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.winsigns.investment.grpc.InstructionOuterClass.Instruction createInstruction(com.winsigns.investment.grpc.InstructionOuterClass.Instruction request) {
      return blockingUnaryCall(
          getChannel(), METHOD_CREATE_INSTRUCTION, getCallOptions(), request);
    }

    /**
     */
    public com.winsigns.investment.grpc.InstructionCommitOuterClass.InstructionCommit commitInstruction(com.winsigns.investment.grpc.InstructionCommitOuterClass.InstructionCommit request) {
      return blockingUnaryCall(
          getChannel(), METHOD_COMMIT_INSTRUCTION, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class investFutureStub extends io.grpc.stub.AbstractStub<investFutureStub> {
    private investFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private investFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected investFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new investFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.winsigns.investment.grpc.InstructionOuterClass.Instruction> createInstruction(
        com.winsigns.investment.grpc.InstructionOuterClass.Instruction request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_CREATE_INSTRUCTION, getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.winsigns.investment.grpc.InstructionCommitOuterClass.InstructionCommit> commitInstruction(
        com.winsigns.investment.grpc.InstructionCommitOuterClass.InstructionCommit request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_COMMIT_INSTRUCTION, getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE_INSTRUCTION = 0;
  private static final int METHODID_COMMIT_INSTRUCTION = 1;

  private static class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final investImplBase serviceImpl;
    private final int methodId;

    public MethodHandlers(investImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CREATE_INSTRUCTION:
          serviceImpl.createInstruction((com.winsigns.investment.grpc.InstructionOuterClass.Instruction) request,
              (io.grpc.stub.StreamObserver<com.winsigns.investment.grpc.InstructionOuterClass.Instruction>) responseObserver);
          break;
        case METHODID_COMMIT_INSTRUCTION:
          serviceImpl.commitInstruction((com.winsigns.investment.grpc.InstructionCommitOuterClass.InstructionCommit) request,
              (io.grpc.stub.StreamObserver<com.winsigns.investment.grpc.InstructionCommitOuterClass.InstructionCommit>) responseObserver);
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
        METHOD_CREATE_INSTRUCTION,
        METHOD_COMMIT_INSTRUCTION);
  }

}
