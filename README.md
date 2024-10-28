<div class="markdown-body"><h1 id="security-jwt-application">Security JWT Application</h1>
<p>This project is a Spring Boot application that implements JWT-based authentication and authorization using Spring Security. It features multiple user roles, secure endpoints, and handles user registration and authentication through a RESTful API.</p>
<h2 id="table-of-contents">Table of Contents</h2>
<ul>
<li><a href="#features">Features</a></li>
<li><a href="#technologies-used">Technologies Used</a></li>
<li><a href="#setup-and-installation">Setup and Installation</a></li>
<li><a href="#usage">Usage</a></li>
<li><a href="#endpoints">Endpoints</a></li>
<li><a href="#contributing">Contributing</a></li>
</ul>
<h2 id="features">Features</h2>
<ul>
<li>User registration and authentication</li>
<li>Role-based access control</li>
<li>JWT token generation and validation</li>
<li>Secure REST endpoints</li>
<li>Custom exception handling</li>
</ul>
<h2 id="technologies-used">Technologies Used</h2>
<ul>
<li><strong>Spring Boot</strong>: Framework used for creating stand-alone, production-grade Spring-based applications.</li>
<li><strong>Spring Security</strong>: Provides security features, including authentication and authorization.</li>
<li><strong>JWT (JSON Web Tokens)</strong>: Used for securing APIs by transmitting information as a JSON object.</li>
<li><strong>H2 Database</strong>: In-memory database for testing purposes.</li>
<li><strong>Lombok</strong>: For reducing boilerplate code in model classes.</li>
</ul>
<h2 id="setup-and-installation">Setup and Installation</h2>
<ol>
<li><strong>Clone the repository</strong>:</li>
</ol>
<pre><code class="bash language-bash hljs">   git <span class="hljs-built_in">clone</span> https://github.com/yourusername/security-jwt-app.git
   <span class="hljs-built_in">cd</span> security-jwt-app</code><button class="copy-ai-code" onclick="copyAICode(this)"><svg stroke="currentColor" fill="none" stroke-width="2" viewBox="0 0 24 24" stroke-linecap="round" stroke-linejoin="round" class="h-4 w-4" height="1em" width="1em" xmlns="http://www.w3.org/2000/svg"><path d="M16 4h2a2 2 0 0 1 2 2v14a2 2 0 0 1-2 2H6a2 2 0 0 1-2-2V6a2 2 0 0 1 2-2h2"></path><rect x="8" y="2" width="8" height="4" rx="1" ry="1"></rect></svg> <span class="label-copy-code">Копировать</span></button></pre>
<ol start="2">
<li><strong>Build the application</strong>:<br>
Ensure you have Maven installed and run:</li>
</ol>
<pre><code class="bash language-bash hljs">   mvn clean install</code></pre>
<ol start="3">
<li><strong>Run the application</strong>:<br>
You can run the application directly from your IDE or use the command:</li>
</ol>
<pre><code class="bash language-bash hljs">   mvn spring-boot:run</code></pre>
<ol start="4">
<li><strong>Access the application</strong>:<br>
The application should be running at <code>http://localhost:8080</code>.</li>
</ol>
<h2 id="usage">Usage</h2>
<h3 id="user-registration">User Registration</h3>
<p>To register a new user, send a POST request to <code>http://localhost:8080/auth/signup</code> with the following JSON body:</p>
<pre><code class="json language-json hljs"><span class="hljs-punctuation">{</span>
  <span class="hljs-attr">"name"</span><span class="hljs-punctuation">:</span> <span class="hljs-string">"username"</span><span class="hljs-punctuation">,</span>
  <span class="hljs-attr">"password"</span><span class="hljs-punctuation">:</span> <span class="hljs-string">"password"</span><span class="hljs-punctuation">,</span>
  <span class="hljs-attr">"role"</span><span class="hljs-punctuation">:</span> <span class="hljs-string">"USER"</span>  <span class="hljs-comment">// or "MODERATOR" or "SUPER_ADMIN"</span>
<span class="hljs-punctuation">}</span></code></pre>
<h3 id="user-sign-in">User Sign In</h3>
<p>To sign in and receive a JWT token, send a POST request to <code>http://localhost:8080/auth/signin</code> with the following JSON body:</p>
<pre><code class="json language-json hljs"><span class="hljs-punctuation">{</span>
  <span class="hljs-attr">"name"</span><span class="hljs-punctuation">:</span> <span class="hljs-string">"username"</span><span class="hljs-punctuation">,</span>
  <span class="hljs-attr">"password"</span><span class="hljs-punctuation">:</span> <span class="hljs-string">"password"</span>
<span class="hljs-punctuation">}</span></code></pre>
<h3 id="product-management-super-admin--moderator">Product Management (Super Admin &amp; Moderator)</h3>
<ul>
<li><p><strong>Retrieve Products</strong>: <code>GET /public/products</code> - Accessible by all users.</p></li>
<li><p><strong>Create Product</strong>: <code>POST /super_admin/save_product</code> - Accessible only by SUPER_ADMIN.</p></li>
<li><p><strong>Update Product</strong>: <code>PUT /moderator/updateProduct</code> - Accessible only by MODERATOR.</p></li>
</ul>
<h2 id="endpoints">Endpoints</h2>
<table>
<thead>
<tr>
<th id="http_method">HTTP Method</th>
<th id="endpoint">Endpoint</th>
<th id="role/access_level">Role/Access Level</th>
</tr>
</thead>
<tbody>
<tr>
<td>POST</td>
<td><code>/auth/signup</code></td>
<td>Public (Registration)</td>
</tr>
<tr>
<td>POST</td>
<td><code>/auth/signin</code></td>
<td>Public (Authentication)</td>
</tr>
<tr>
<td>GET</td>
<td><code>/public/products</code></td>
<td>Public (No Authentication)</td>
</tr>
<tr>
<td>POST</td>
<td><code>/super_admin/save_product</code></td>
<td>SUPER_ADMIN</td>
</tr>
<tr>
<td>PUT</td>
<td><code>/moderator/updateProduct</code></td>
<td>MODERATOR</td>
</tr>
</tbody>
</table>
<h2 id="contributing">Contributing</h2>
<p>Contributions are welcome! Please feel free to submit a pull request or open an issue to discuss any enhancements or bugs.</p>
<ol>
<li>Fork the repository</li>
<li>Create your feature branch (<code>git checkout -b feature/AmazingFeature</code>)</li>
<li>Commit your changes (<code>git commit -m 'Add some AmazingFeature'</code>)</li>
<li>Push to the branch (<code>git push origin feature/AmazingFeature</code>)</li>
<li>Open a pull request</li>
</ol>
