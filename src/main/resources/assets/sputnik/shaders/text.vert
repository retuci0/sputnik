#version 150
in vec3 aPos;
in vec2 aTexCoord;
in vec4 aColor;

out vec2 vTexCoord;
out vec4 vColor;

uniform mat4 u_ProjectionMatrix;

void main() {
    gl_Position = u_ProjectionMatrix * vec4(aPos, 1.0);
    vTexCoord = aTexCoord;
    vColor = aColor;
}