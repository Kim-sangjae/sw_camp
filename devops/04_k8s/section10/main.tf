############################################
# Kubernetes Provider
############################################
provider "kubernetes" {
  config_path = "~/.kube/config"
}

############################################
# Vue.js Deployment
############################################
resource "kubernetes_deployment" "vue002dep" {
  metadata {
    name = "vue002dep"
  }

  spec {
    replicas = var.replicas_vue

    selector {
      match_labels = {
        app = "vue002kube"
      }
    }

    template {
      metadata {
        labels = {
          app = "vue002kube"
        }
      }

      spec {
        container {
          name  = "vue-container"
          image = var.image_vue

          port {
            container_port = 80
          }
        }
      }
    }
  }
}

############################################
# Vue.js Service
############################################
resource "kubernetes_service" "vue002ser" {
  metadata {
    name = "vue002ser"
  }

  spec {
    selector = {
      app = "vue002kube"
    }

    port {
      port        = 8000
      target_port = 80
    }

    type = "ClusterIP"
  }
}

############################################
# Spring Boot Deployment
############################################
resource "kubernetes_deployment" "boot002dep" {
  metadata {
    name = "boot002dep"
  }

  spec {
    replicas = var.replicas_boot

    selector {
      match_labels = {
        app = "boot002kube"
      }
    }

    template {
      metadata {
        labels = {
          app = "boot002kube"
        }
      }

      spec {
        container {
          name  = "boot-container"
          image = var.image_boot
          image_pull_policy = "Always"

          port {
            container_port = 8080
          }
        }
      }
    }
  }
}

############################################
# Spring Boot Service
############################################
resource "kubernetes_service" "boot002ser" {
  metadata {
    name = "boot002ser"
  }

  spec {
    selector = {
      app = "boot002kube"
    }

    port {
      port        = 8001
      target_port = 8080
    }

    type = "ClusterIP"
  }
}

############################################
# Ingress (nginx)
############################################
resource "kubernetes_ingress_v1" "sw_camp_ingress" {
  metadata {
    name = "sw-camp-ingress"
    annotations = {
      "nginx.ingress.kubernetes.io/ssl-redirect" = "false"
      "nginx.ingress.kubernetes.io/rewrite-target" = "/$2"
    }
  }

  spec {
    ingress_class_name = "nginx"

    rule {
      http {
        # Vue
        path {
          path = "/()(.*)$"
          path_type = "ImplementationSpecific"
          backend {
            service {
              name = kubernetes_service.vue002ser.metadata[0].name
              port {
                number = 8000
              }
            }
          }
        }

        # Spring Boot
        path {
          path = "/boot(/|$)(.*)$"
          path_type = "ImplementationSpecific"
          backend {
            service {
              name = kubernetes_service.boot002ser.metadata[0].name
              port {
                number = 8001
              }
            }
          }
        }
      }
    }
  }
}
