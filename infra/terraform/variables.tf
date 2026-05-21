variable "aws_region" {
  default = "us-east-1"
}

variable "db_host" {
  description = "Hostname of the existing RDS MySQL instance"
  type        = string
  default     = "database-1.c8vcuqew0oei.us-east-1.rds.amazonaws.com"
}

variable "db_password" {
  description = "Password for the existing RDS MySQL instance"
  type        = string
  default     = "password"
}

variable "ecr_account_id" {
  description = "AWS account ID for ECR image URI"
  type        = string
}
