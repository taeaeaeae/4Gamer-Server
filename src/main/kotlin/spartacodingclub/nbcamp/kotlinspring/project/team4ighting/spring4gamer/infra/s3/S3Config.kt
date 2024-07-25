package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.infra.s3

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class S3Config(
    @Value("\${spring.cloud.aws.credentials.access-key}")
    private var accessKey: String,

    @Value("\${spring.cloud.aws.credentials.secret-key}")
private var secretKey: String,

@Value("\${spring.cloud.aws.region.static}")
private var region: String
) {

    @Bean
    fun amazonS3Client(): AmazonS3Client {
        val awsCredentials = BasicAWSCredentials(accessKey, secretKey)
        return AmazonS3ClientBuilder.standard()
            .withRegion(region)
            .withCredentials(AWSStaticCredentialsProvider(awsCredentials))
            .build() as AmazonS3Client
    }
}