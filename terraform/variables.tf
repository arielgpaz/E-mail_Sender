variable "region" {
  description = "Região da AWS que está em uso"
  type        = string
  default     = "us-east-2"
}

variable "account" {
  description = "ID da conta da AWS"
  type        = string
  default     = "261765644132"
}

variable "vpc_id" {
  description = "ID da VPC da AWS"
  type        = string
  default     = "vpc-074f2ee2c79543d11"
}

variable private_key_path{
  description = "Path to the SSH private key to be used for authentication"
  default = "~/.ssh/id_ed25519"
}

variable public_key_path{
  description = "Path to the SSH public key to be used for authentication"
  default = "~/.ssh/id_ed25519.pub"
}

variable cidr_open{
  description = "CIDR liberando qualquer IP"
  default = "0.0.0.0/0"
}
