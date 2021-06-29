
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sqs.SqsClient
import software.amazon.awssdk.services.sqs.model.SendMessageRequest
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest

object SqsHandle {
  val movieQueueURL="https://sqs.REGION.amazonaws.com/QUEUE/movieQ"

  val  sqsClient : SqsClient = SqsClient.builder()
    .region(Region.US_EAST_1)
    .build();

  def sendMovieMessage(movieTitle: String): Unit ={
    val buildMovieMessage = SendMessageRequest.builder.queueUrl(movieQueueURL)
      .messageBody(movieTitle)
      .build
    sqsClient.sendMessage(buildMovieMessage)
  }

  def getMovieMessage: Unit ={
    val receiveMessageRequest = ReceiveMessageRequest.builder.queueUrl(movieQueueURL).maxNumberOfMessages(5).build
    val movieMessages = sqsClient.receiveMessage(receiveMessageRequest).messages

    println("RECEIVED MESSAGE")
    movieMessages.forEach(a=> println(a.body()))
  }

  def main(args: Array[String]): Unit = {
    sendMovieMessage("MOVIE- Ramayana: The Legend of Prince Rama")
    getMovieMessage
  }
}
