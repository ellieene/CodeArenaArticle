package com.example.article.client.grpc;

import com.example.article.exception.EntityNotFoundException;
import com.example.article.model.request.PointsAndUserIdRequest;
import com.example.article.model.response.StringResponse;
import com.example.grpc.profileAndArticle.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import static com.example.article.util.CommonStrings.*;

@Service
public class ProfileGrpcClient {

    private final String profileServiceAddress = "codearena-profile:50051";
    private final AtomicReference<UserServiceGrpc.UserServiceBlockingStub> lazyStub = new AtomicReference<>();

    private UserServiceGrpc.UserServiceBlockingStub getStub() {
        if (lazyStub.get() == null) {
            synchronized (lazyStub) {
                if (lazyStub.get() == null) {
                    ManagedChannel channel = ManagedChannelBuilder
                            .forTarget(profileServiceAddress)
                            .usePlaintext()
                            .build();
                    lazyStub.set(UserServiceGrpc.newBlockingStub(channel));
                }
            }
        }
        return lazyStub.get();
    }

    public PointsAndUserIdRequest getPointsAndUserId(String username) {
        try {
            PointsRequest request = PointsRequest.newBuilder()
                    .setUsername(username)
                    .build();

            PointsResponse response = getStub().getPointsAndUserId(request);

            return new PointsAndUserIdRequest(
                    response.getPoints(),
                    UUID.fromString(response.getUserId())
            );

        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode().name().equals("NOT_FOUND")) {
                throw new EntityNotFoundException(NOT_FOUND_USER);
            }
            throw new RuntimeException("Failed to get username: " + e.getStatus().getDescription(), e);
        }
    }

    public StringResponse purchasingAnArticle(String username, int points) {
        try {
            PurchasingRequest request = PurchasingRequest.newBuilder()
                    .setUsername(username)
                    .setPoints(points)
                    .build();

            PurchasingResponse response = getStub().purchasingAnArticle(request);
            if (!response.getPurchasing()) {
                throw new RuntimeException(FAILED_TO_THE_PURCHASING);
            }
            return new StringResponse(
                    ARTICLE_SUCCESS
            );
        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode().name().equals("NOT_FOUND")) {
                throw new EntityNotFoundException(NOT_FOUND_USER);
            }
            throw new RuntimeException("Failed to purchasing article: " + e.getStatus().getDescription(), e);
        }
    }
}