package com.example.article.service.grpc;

import com.example.grpc.user.CheckRequest;
import com.example.grpc.user.CheckResponse;
import com.example.grpc.user.UserServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class ProfileGrpcClient {

    @GrpcClient("profileService")
    private UserServiceGrpc.UserServiceBlockingStub userStub;

    public boolean checkUuidAndUsername(String username, String userId) {
        CheckRequest request = CheckRequest.newBuilder()
                .setUsername(username)
                .setUserId(userId)
                .build();

        CheckResponse response = userStub.checkUuidAndUsername(request);
        return response.getIsValid();
    }
}
