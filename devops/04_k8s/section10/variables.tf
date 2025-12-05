variable "image_vue" {
  default = "limu810/k8s-vue-ing:latest"
}

variable "image_boot" {
  default = "limu810/k8s-boot-ing:latest"
}

variable "replicas_vue" {
  default = 3
}

variable "replicas_boot" {
  default = 1
}