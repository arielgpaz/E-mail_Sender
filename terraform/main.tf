terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 4.16"
    }
  }

  required_version = ">= 1.2.0"
}

provider "aws" {
  region = var.region
}

resource "aws_key_pair" "email_sender_key" {
  key_name   = "email_sender_key"
  public_key = "${file(var.public_key_path)}"
}

resource "aws_security_group" "email_sender_sg" {
  name        = "email_sender_sg"
  description = "Used in the terraform"
  # vpc_id      = "${aws_vpc.default.id}"

  # SSH access from anywhere
  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = [var.cidr_open]
  }

  # HTTP access from the internet
  egress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = [var.cidr_open]
  }

  # outbound internet access
  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = [var.cidr_open]
  }
}


resource "aws_instance" "email_sender_ec2" {
  ami                    = "ami-0e83be366243f524a"
  instance_type          = "t2.micro"
  key_name               = aws_key_pair.email_sender_key.id
  vpc_security_group_ids = [aws_security_group.email_sender_sg.id]

  connection {
    user        = "ubuntu"
    type        = "ssh"
    private_key = "${file(var.private_key_path)}"
    host        = self.public_ip
  }

  # install java, create dir
  provisioner "remote-exec" {
    inline = [
      "sudo apt-get -y update",
      "sudo apt-get -y install openjdk-17-jre",
    ]
  }

  # upload jar file
  provisioner "file" {
    source      = "~/OneDrive/Estudos/TI/Java/Projetos/email-sender/target/grades_email_sender.jar"
    destination = "/home/ubuntu/grades_email_sender.jar"
  }

  # run jar
  provisioner "remote-exec" {
    inline = [
      "java -jar grades_email_sender.jar",
    ]
  }
}
