package com.example.article.service.grpc;

import com.example.article.exception.EntityNotFoundException;
import com.example.article.model.request.PointsAndUserIdRequest;
import com.example.article.model.response.StringResponse;
import com.example.grpc.user.*;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.example.article.util.CommonStrings.*;

@Service
public class ProfileGrpcClient {

    @GrpcClient("profileService")
    private UserServiceGrpc.UserServiceBlockingStub userStub;

    public PointsAndUserIdRequest getPointsAndUserId(String username) {
        try {
            PointsRequest request = PointsRequest.newBuilder()
                    .setUsername(username)
                    .build();

            PointsResponse response = userStub.getPointsAndUserId(request);

            return new PointsAndUserIdRequest(
                    response.getPoints(),
                    UUID.fromString(response.getUserId())
            );

        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode() == Status.Code.NOT_FOUND) {
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

            PurchasingResponse response = userStub.purchasingAnArticle(request);
            if (!response.getPurchasing()) {
                throw new RuntimeException(FAILED_TO_THE_PURCHASING);
            }
            return new StringResponse(
                    ARTICLE_SUCCESS
            );
        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode() == Status.Code.NOT_FOUND) {
                throw new EntityNotFoundException(NOT_FOUND_USER);
            }
            throw new RuntimeException("Failed to get username: " + e.getStatus().getDescription(), e);
        }
    }
}
