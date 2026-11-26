-- =====================================================
-- MIGRACIÓN DEL MÓDULO DE AUTENTICACIÓN
-- TALLER DE BETANCOURT
-- =====================================================

-- Agrega el campo rol si todavía no existe.
ALTER TABLE usuarios
ADD COLUMN IF NOT EXISTS rol VARCHAR(40)
NOT NULL DEFAULT 'AUXILIAR';


-- =====================================================
-- ASIGNACIÓN DE ROLES
-- =====================================================

-- IMPORTANTE:
-- Cambia los correos de ejemplo por los correos reales.

-- Administrador principal.
-- UPDATE usuarios
-- SET rol = 'SUPER_ADMIN'
-- WHERE correo = 'admin@betancourt.com';


-- Personal administrativo.
-- UPDATE usuarios
-- SET rol = 'ADMINISTRATIVO'
-- WHERE correo = 'administrativo@betancourt.com';


-- Auxiliares.
-- UPDATE usuarios
-- SET rol = 'AUXILIAR'
-- WHERE correo = 'auxiliar@betancourt.com';


-- =====================================================
-- CONSULTA DE VERIFICACIÓN
-- =====================================================

SELECT
    idusuario,
    nombre,
    apellido,
    correo,
    estado,
    rol
FROM usuarios
ORDER BY idusuario;