output "vue_service_ip" {
  value = kubernetes_service.vue002ser.spec[0].cluster_ip
}