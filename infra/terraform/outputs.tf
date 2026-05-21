output "api_gateway_url" {
  value = aws_apigatewayv2_api.http_api.api_endpoint
}

output "ecs_cluster_name" {
  value = aws_ecs_cluster.main.name
}
