#version 150
in vec2 vTexCoord;
in vec4 vColor;

out vec4 fragColor;

uniform sampler2D u_Texture;

void main() {
    float distance = texture(u_Texture, vTexCoord).r;
    float alpha = smoothstep(0.4, 0.6, distance);

    fragColor = vec4(vColor.rgb, vColor.a * alpha);
}